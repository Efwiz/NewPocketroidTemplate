package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: JsIrProgramFragment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\b0\u00070\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0003R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgram;", "", "modules", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModule;", "(Ljava/util/List;)V", "asCrossModuleDependencies", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleReferences;", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "relativeRequirePath", "", "asFragments", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIrProgram {
    private List<JsIrModule> modules;

    public JsIrProgram(List<JsIrModule> modules) {
        Intrinsics.checkNotNullParameter(modules, "modules");
        this.modules = modules;
    }

    public final List<Pair<JsIrModule, CrossModuleReferences>> asCrossModuleDependencies(ModuleKind moduleKind, boolean relativeRequirePath) {
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        List<JsIrModule> list = this.modules;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((JsIrModule) it.next()).makeModuleHeader());
        }
        CrossModuleDependenciesResolver crossModuleDependenciesResolver = new CrossModuleDependenciesResolver(moduleKind, arrayList);
        this.modules = CollectionsKt.emptyList();
        Set<Map.Entry<JsIrModuleHeader, CrossModuleReferences>> setEntrySet = crossModuleDependenciesResolver.resolveCrossModuleDependencies(relativeRequirePath).entrySet();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10));
        Iterator<T> it2 = setEntrySet.iterator();
        while (it2.hasNext()) {
            Map.Entry entry = (Map.Entry) it2.next();
            JsIrModule associatedModule = ((JsIrModuleHeader) entry.getKey()).getAssociatedModule();
            if (associatedModule == null) {
                throw new IllegalStateException(("Internal error: module " + ((JsIrModuleHeader) entry.getKey()).getModuleName() + " is not loaded").toString());
            }
            ((CrossModuleReferences) entry.getValue()).initJsImportsForModule(associatedModule);
            arrayList2.add(TuplesKt.to(associatedModule, entry.getValue()));
        }
        return arrayList2;
    }

    public final List<JsIrProgramFragment> asFragments() {
        List<JsIrModule> list = this.modules;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((JsIrModule) it.next()).getFragments());
        }
        ArrayList arrayList2 = arrayList;
        this.modules = CollectionsKt.emptyList();
        return arrayList2;
    }
}
