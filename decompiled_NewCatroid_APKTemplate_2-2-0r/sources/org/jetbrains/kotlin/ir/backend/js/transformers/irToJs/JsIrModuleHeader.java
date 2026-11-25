package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JsIrProgramFragment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u00002\u00020\u0001B[\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R!\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0017\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\b¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0013R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleHeader;", "", "moduleName", "", "externalModuleName", "definitions", "", "nameBindings", "", "optionalCrossModuleImports", "reexportedInModuleWithName", "associatedModule", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModule;", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;Ljava/util/Map;Ljava/util/Set;Ljava/lang/String;Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModule;)V", "getAssociatedModule", "()Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModule;", "setAssociatedModule", "(Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModule;)V", "getDefinitions", "()Ljava/util/Set;", "getExternalModuleName", "()Ljava/lang/String;", "externalNames", "getExternalNames", "externalNames$delegate", "Lkotlin/Lazy;", "getModuleName", "getNameBindings", "()Ljava/util/Map;", "getOptionalCrossModuleImports", "getReexportedInModuleWithName", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIrModuleHeader {
    private JsIrModule associatedModule;
    private final Set<String> definitions;
    private final String externalModuleName;

    /* JADX INFO: renamed from: externalNames$delegate, reason: from kotlin metadata */
    private final Lazy externalNames;
    private final String moduleName;
    private final Map<String, String> nameBindings;
    private final Set<String> optionalCrossModuleImports;
    private final String reexportedInModuleWithName;

    public JsIrModuleHeader(String moduleName, String externalModuleName, Set<String> definitions, Map<String, String> nameBindings, Set<String> optionalCrossModuleImports, String str, JsIrModule jsIrModule) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(externalModuleName, "externalModuleName");
        Intrinsics.checkNotNullParameter(definitions, "definitions");
        Intrinsics.checkNotNullParameter(nameBindings, "nameBindings");
        Intrinsics.checkNotNullParameter(optionalCrossModuleImports, "optionalCrossModuleImports");
        this.moduleName = moduleName;
        this.externalModuleName = externalModuleName;
        this.definitions = definitions;
        this.nameBindings = nameBindings;
        this.optionalCrossModuleImports = optionalCrossModuleImports;
        this.reexportedInModuleWithName = str;
        this.associatedModule = jsIrModule;
        this.externalNames = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Set<? extends String>>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrModuleHeader$externalNames$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends String> invoke() {
                return SetsKt.minus((Set) this.this$0.getNameBindings().keySet(), (Iterable) this.this$0.getDefinitions());
            }
        });
    }

    public /* synthetic */ JsIrModuleHeader(String str, String str2, Set set, Map map, Set set2, String str3, JsIrModule jsIrModule, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, set, map, set2, (i & 32) != 0 ? null : str3, jsIrModule);
    }

    public final String getModuleName() {
        return this.moduleName;
    }

    public final String getExternalModuleName() {
        return this.externalModuleName;
    }

    public final Set<String> getDefinitions() {
        return this.definitions;
    }

    public final Map<String, String> getNameBindings() {
        return this.nameBindings;
    }

    public final Set<String> getOptionalCrossModuleImports() {
        return this.optionalCrossModuleImports;
    }

    public final String getReexportedInModuleWithName() {
        return this.reexportedInModuleWithName;
    }

    public final JsIrModule getAssociatedModule() {
        return this.associatedModule;
    }

    public final void setAssociatedModule(JsIrModule jsIrModule) {
        this.associatedModule = jsIrModule;
    }

    public final Set<String> getExternalNames() {
        return (Set) this.externalNames.getValue();
    }
}
