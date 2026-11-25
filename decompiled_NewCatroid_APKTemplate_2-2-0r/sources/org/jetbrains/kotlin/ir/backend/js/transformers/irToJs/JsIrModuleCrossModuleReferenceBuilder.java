package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.serialization.CityHashKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.ir.backend.js.utils.NameTablesKt;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: JsIrProgramFragment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010#\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t¢\u0006\u0002\u0010\nJ\u0006\u0010\"\u001a\u00020#J\u0010\u0010$\u001a\u00020%2\b\b\u0002\u0010&\u001a\u00020'J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\r2\u0006\u0010(\u001a\u00020\u0005H\u0002J\f\u0010)\u001a\u00020**\u00020\u0005H\u0002R&\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001c¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleCrossModuleReferenceBuilder;", "", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "header", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "relativeRequirePath", "", "transitiveExportFrom", "", "(Lorg/jetbrains/kotlin/serialization/js/ModuleKind;Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;ZLjava/util/List;)V", "exportNames", "", "", "getExportNames", "()Ljava/util/Map;", "setExportNames", "(Ljava/util/Map;)V", PsiKeyword.EXPORTS, "", "getExports", "()Ljava/util/Set;", "getHeader", "()Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "imports", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleRef;", "getImports", "()Ljava/util/List;", "getModuleKind", "()Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "getRelativeRequirePath", "()Z", "getTransitiveExportFrom", "buildCrossModuleRefs", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleReferences;", "buildExportNames", "", "startIndex", "", "moduleHeader", "toJsImportedModule", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class JsIrModuleCrossModuleReferenceBuilder {
    public Map<String, String> exportNames;
    private final Set<String> exports;
    private final JsIrModuleHeader header;
    private final List<CrossModuleRef> imports;
    private final ModuleKind moduleKind;
    private final boolean relativeRequirePath;
    private final List<JsIrModuleHeader> transitiveExportFrom;

    public JsIrModuleCrossModuleReferenceBuilder(ModuleKind moduleKind, JsIrModuleHeader header, boolean z, List<JsIrModuleHeader> transitiveExportFrom) {
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        Intrinsics.checkNotNullParameter(header, "header");
        Intrinsics.checkNotNullParameter(transitiveExportFrom, "transitiveExportFrom");
        this.moduleKind = moduleKind;
        this.header = header;
        this.relativeRequirePath = z;
        this.transitiveExportFrom = transitiveExportFrom;
        this.imports = new ArrayList();
        this.exports = new LinkedHashSet();
    }

    public final ModuleKind getModuleKind() {
        return this.moduleKind;
    }

    public final JsIrModuleHeader getHeader() {
        return this.header;
    }

    public final boolean getRelativeRequirePath() {
        return this.relativeRequirePath;
    }

    public final List<JsIrModuleHeader> getTransitiveExportFrom() {
        return this.transitiveExportFrom;
    }

    public final List<CrossModuleRef> getImports() {
        return this.imports;
    }

    public final Set<String> getExports() {
        return this.exports;
    }

    public final Map<String, String> getExportNames() {
        Map<String, String> map = this.exportNames;
        if (map != null) {
            return map;
        }
        Intrinsics.throwUninitializedPropertyAccessException("exportNames");
        return null;
    }

    public final void setExportNames(Map<String, String> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.exportNames = map;
    }

    public static /* synthetic */ void buildExportNames$default(JsIrModuleCrossModuleReferenceBuilder jsIrModuleCrossModuleReferenceBuilder, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        jsIrModuleCrossModuleReferenceBuilder.buildExportNames(i);
    }

    public final void buildExportNames(int startIndex) {
        String jsIdentifier;
        List listSorted = CollectionsKt.sorted(this.exports);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(listSorted, 10)), 16));
        for (Object obj : listSorted) {
            LinkedHashMap linkedHashMap2 = linkedHashMap;
            String str = (String) obj;
            if (this.moduleKind == ModuleKind.ES) {
                jsIdentifier = this.header.getNameBindings().get(str) + CityHashKt.cityHash64String(str);
            } else {
                jsIdentifier = NameTablesKt.toJsIdentifier(startIndex);
                startIndex++;
            }
            linkedHashMap2.put(obj, jsIdentifier);
        }
        setExportNames(linkedHashMap);
    }

    public final CrossModuleReferences buildCrossModuleRefs() {
        CrossModuleTransitiveExport crossModuleTransitiveExport;
        boolean z = this.moduleKind == ModuleKind.ES;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        List<CrossModuleRef> list = this.imports;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (CrossModuleRef crossModuleRef : list) {
            String tag = crossModuleRef.getTag();
            String str = crossModuleRef.getModule().getExportNames().get(tag);
            Intrinsics.checkNotNull(str);
            Pair pair = TuplesKt.to(tag, new CrossModuleImport(str, buildCrossModuleRefs$import(z, this, linkedHashMap, crossModuleRef.getModule().header)));
            linkedHashMap2.put(pair.getFirst(), pair.getSecond());
        }
        List<JsIrModuleHeader> list2 = this.transitiveExportFrom;
        ArrayList arrayList = new ArrayList();
        for (JsIrModuleHeader jsIrModuleHeader : list2) {
            if (jsIrModuleHeader.getReexportedInModuleWithName() != null) {
                JsName internalName = buildCrossModuleRefs$import(z, this, linkedHashMap, jsIrModuleHeader).getInternalName();
                String strRelativeRequirePath = relativeRequirePath(jsIrModuleHeader);
                if (strRelativeRequirePath == null) {
                    strRelativeRequirePath = jsIrModuleHeader.getExternalModuleName();
                }
                crossModuleTransitiveExport = new CrossModuleTransitiveExport(internalName, strRelativeRequirePath);
            } else {
                crossModuleTransitiveExport = null;
            }
            if (crossModuleTransitiveExport != null) {
                arrayList.add(crossModuleTransitiveExport);
            }
        }
        return new CrossModuleReferences(this.moduleKind, CollectionsKt.toList(linkedHashMap.values()), arrayList, getExportNames(), linkedHashMap2);
    }

    private static final JsImportedModule buildCrossModuleRefs$import(boolean z, JsIrModuleCrossModuleReferenceBuilder jsIrModuleCrossModuleReferenceBuilder, Map<JsIrModuleHeader, JsImportedModule> map, JsIrModuleHeader jsIrModuleHeader) {
        if (z) {
            return jsIrModuleCrossModuleReferenceBuilder.toJsImportedModule(jsIrModuleHeader);
        }
        JsImportedModule jsImportedModule = map.get(jsIrModuleHeader);
        if (jsImportedModule == null) {
            jsImportedModule = jsIrModuleCrossModuleReferenceBuilder.toJsImportedModule(jsIrModuleHeader);
            map.put(jsIrModuleHeader, jsImportedModule);
        }
        return jsImportedModule;
    }

    private final JsImportedModule toJsImportedModule(JsIrModuleHeader jsIrModuleHeader) {
        return new JsImportedModule(jsIrModuleHeader.getExternalModuleName(), new JsName(jsIrModuleHeader.getModuleName(), false), null, relativeRequirePath(jsIrModuleHeader));
    }

    private final String relativeRequirePath(JsIrModuleHeader moduleHeader) {
        if (!this.relativeRequirePath) {
            return null;
        }
        File parentFile = new File(this.header.getExternalModuleName()).getParentFile();
        if (parentFile == null) {
            return "./" + moduleHeader.getExternalModuleName();
        }
        String relativeString = FilesKt.toRelativeString(new File(moduleHeader.getExternalModuleName()), parentFile);
        String separator = File.separator;
        Intrinsics.checkNotNullExpressionValue(separator, "separator");
        String strReplace$default = StringsKt.replace$default(relativeString, separator, "/", false, 4, (Object) null);
        String str = StringsKt.startsWith$default(strReplace$default, "../", false, 2, (Object) null) ? strReplace$default : null;
        return str == null ? "./" + strReplace$default : str;
    }
}
