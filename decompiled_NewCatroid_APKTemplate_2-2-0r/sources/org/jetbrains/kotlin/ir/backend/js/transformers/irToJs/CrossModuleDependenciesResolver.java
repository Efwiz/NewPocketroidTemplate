package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: JsIrProgramFragment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\fR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleDependenciesResolver;", "", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "headers", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "(Lorg/jetbrains/kotlin/serialization/js/ModuleKind;Ljava/util/List;)V", "resolveCrossModuleDependencies", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleReferences;", "relativeRequirePath", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CrossModuleDependenciesResolver {
    private final List<JsIrModuleHeader> headers;
    private final ModuleKind moduleKind;

    public CrossModuleDependenciesResolver(ModuleKind moduleKind, List<JsIrModuleHeader> headers) {
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        Intrinsics.checkNotNullParameter(headers, "headers");
        this.moduleKind = moduleKind;
        this.headers = headers;
    }

    public final Map<JsIrModuleHeader, CrossModuleReferences> resolveCrossModuleDependencies(boolean relativeRequirePath) {
        List<JsIrModuleHeader> list = this.headers;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : list) {
            String reexportedInModuleWithName = ((JsIrModuleHeader) obj).getReexportedInModuleWithName();
            Object obj2 = linkedHashMap.get(reexportedInModuleWithName);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(reexportedInModuleWithName, obj2);
            }
            ((List) obj2).add(obj);
        }
        List<JsIrModuleHeader> list2 = this.headers;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list2, 10)), 16));
        for (Object obj3 : list2) {
            LinkedHashMap linkedHashMap3 = linkedHashMap2;
            JsIrModuleHeader jsIrModuleHeader = (JsIrModuleHeader) obj3;
            ModuleKind moduleKind = this.moduleKind;
            List listEmptyList = (List) linkedHashMap.get(jsIrModuleHeader.getModuleName());
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            linkedHashMap3.put(obj3, new JsIrModuleCrossModuleReferenceBuilder(moduleKind, jsIrModuleHeader, relativeRequirePath, listEmptyList));
        }
        LinkedHashMap linkedHashMap4 = linkedHashMap2;
        LinkedHashMap linkedHashMap5 = new LinkedHashMap();
        for (JsIrModuleHeader jsIrModuleHeader2 : this.headers) {
            Object obj4 = linkedHashMap4.get(jsIrModuleHeader2);
            Intrinsics.checkNotNull(obj4);
            JsIrModuleCrossModuleReferenceBuilder jsIrModuleCrossModuleReferenceBuilder = (JsIrModuleCrossModuleReferenceBuilder) obj4;
            for (String str : jsIrModuleHeader2.getDefinitions()) {
                if (linkedHashMap5.containsKey(str)) {
                    throw new IllegalArgumentException(("Duplicate definition: " + str).toString());
                }
                linkedHashMap5.put(str, jsIrModuleCrossModuleReferenceBuilder);
            }
        }
        for (JsIrModuleHeader jsIrModuleHeader3 : this.headers) {
            Object obj5 = linkedHashMap4.get(jsIrModuleHeader3);
            Intrinsics.checkNotNull(obj5);
            JsIrModuleCrossModuleReferenceBuilder jsIrModuleCrossModuleReferenceBuilder2 = (JsIrModuleCrossModuleReferenceBuilder) obj5;
            for (String str2 : jsIrModuleHeader3.getExternalNames()) {
                JsIrModuleCrossModuleReferenceBuilder jsIrModuleCrossModuleReferenceBuilder3 = (JsIrModuleCrossModuleReferenceBuilder) linkedHashMap5.get(str2);
                if (jsIrModuleCrossModuleReferenceBuilder3 == null) {
                    if (!jsIrModuleHeader3.getOptionalCrossModuleImports().contains(str2)) {
                        String str3 = jsIrModuleHeader3.getNameBindings().get(str2);
                        if (str3 == null) {
                            str3 = "<unknown name>";
                        }
                        throw new IllegalStateException(("Internal error: cannot find external signature '" + str2 + "' for name '" + str3 + "' in module " + jsIrModuleHeader3.getModuleName()).toString());
                    }
                } else {
                    jsIrModuleCrossModuleReferenceBuilder2.getImports().add(new CrossModuleRef(jsIrModuleCrossModuleReferenceBuilder3, str2));
                    jsIrModuleCrossModuleReferenceBuilder3.getExports().add(str2);
                }
            }
        }
        Iterator it = linkedHashMap4.entrySet().iterator();
        while (it.hasNext()) {
            JsIrModuleCrossModuleReferenceBuilder.buildExportNames$default((JsIrModuleCrossModuleReferenceBuilder) ((Map.Entry) it.next()).getValue(), 0, 1, null);
        }
        List<JsIrModuleHeader> list3 = this.headers;
        LinkedHashMap linkedHashMap6 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list3, 10)), 16));
        for (Object obj6 : list3) {
            Object obj7 = linkedHashMap4.get((JsIrModuleHeader) obj6);
            Intrinsics.checkNotNull(obj7);
            linkedHashMap6.put(obj6, ((JsIrModuleCrossModuleReferenceBuilder) obj7).buildCrossModuleRefs());
        }
        return linkedHashMap6;
    }
}
