package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsName;

/* JADX INFO: compiled from: JsIrProgramFragment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\tJ\u0006\u0010\u0010\u001a\u00020\u0011R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000b¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModule;", "", "moduleName", "", "externalModuleName", "fragments", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "reexportedInModuleWithName", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;)V", "getExternalModuleName", "()Ljava/lang/String;", "getFragments", "()Ljava/util/List;", "getModuleName", "getReexportedInModuleWithName", "makeModuleHeader", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIrModule {
    private final String externalModuleName;
    private final List<JsIrProgramFragment> fragments;
    private final String moduleName;
    private final String reexportedInModuleWithName;

    public JsIrModule(String moduleName, String externalModuleName, List<JsIrProgramFragment> fragments, String str) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(externalModuleName, "externalModuleName");
        Intrinsics.checkNotNullParameter(fragments, "fragments");
        this.moduleName = moduleName;
        this.externalModuleName = externalModuleName;
        this.fragments = fragments;
        this.reexportedInModuleWithName = str;
    }

    public /* synthetic */ JsIrModule(String str, String str2, List list, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, list, (i & 8) != 0 ? null : str3);
    }

    public final String getModuleName() {
        return this.moduleName;
    }

    public final String getExternalModuleName() {
        return this.externalModuleName;
    }

    public final List<JsIrProgramFragment> getFragments() {
        return this.fragments;
    }

    public final String getReexportedInModuleWithName() {
        return this.reexportedInModuleWithName;
    }

    public final JsIrModuleHeader makeModuleHeader() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        HashSet hashSet = new HashSet();
        boolean z = false;
        for (JsIrProgramFragment jsIrProgramFragment : this.fragments) {
            z = z || !jsIrProgramFragment.getExports().isEmpty();
            for (Map.Entry<String, JsName> entry : jsIrProgramFragment.getNameBindings().entrySet()) {
                String key = entry.getKey();
                String string = entry.getValue().toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                linkedHashMap.put(key, string);
            }
            CollectionsKt.addAll(linkedHashSet, jsIrProgramFragment.getDefinitions());
            CollectionsKt.addAll(hashSet, jsIrProgramFragment.getOptionalCrossModuleImports());
        }
        String str = this.moduleName;
        String str2 = this.externalModuleName;
        HashSet hashSet2 = hashSet;
        String str3 = this.reexportedInModuleWithName;
        if (!z) {
            str3 = null;
        }
        return new JsIrModuleHeader(str, str2, linkedHashSet, linkedHashMap, hashSet2, str3, this);
    }
}
