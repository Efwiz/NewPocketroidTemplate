package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JsIrProgramFragment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleRef;", "", "module", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleCrossModuleReferenceBuilder;", "tag", "", "(Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleCrossModuleReferenceBuilder;Ljava/lang/String;)V", "getModule", "()Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModuleCrossModuleReferenceBuilder;", "getTag", "()Ljava/lang/String;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class CrossModuleRef {
    private final JsIrModuleCrossModuleReferenceBuilder module;
    private final String tag;

    public CrossModuleRef(JsIrModuleCrossModuleReferenceBuilder module, String tag) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(tag, "tag");
        this.module = module;
        this.tag = tag;
    }

    public final JsIrModuleCrossModuleReferenceBuilder getModule() {
        return this.module;
    }

    public final String getTag() {
        return this.tag;
    }
}
