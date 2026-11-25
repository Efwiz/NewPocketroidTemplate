package org.jetbrains.kotlin.js.translate.context;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsName;

/* JADX INFO: compiled from: InlineFunctionContext.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u001d\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\b¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/context/InlineFunctionContext;", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)V", "declarationsBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "getDeclarationsBlock", "()Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "importBlock", "getImportBlock", "imports", "", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getImports", "()Ljava/util/Map;", "prototypeBlock", "getPrototypeBlock", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineFunctionContext {
    private final JsCompositeBlock declarationsBlock;
    private final CallableDescriptor descriptor;
    private final JsCompositeBlock importBlock;
    private final Map<String, JsName> imports;
    private final JsCompositeBlock prototypeBlock;

    public InlineFunctionContext(CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        this.descriptor = descriptor;
        this.imports = new LinkedHashMap();
        this.importBlock = new JsCompositeBlock();
        this.prototypeBlock = new JsCompositeBlock();
        this.declarationsBlock = new JsCompositeBlock();
    }

    public final CallableDescriptor getDescriptor() {
        return this.descriptor;
    }

    public final Map<String, JsName> getImports() {
        return this.imports;
    }

    public final JsCompositeBlock getImportBlock() {
        return this.importBlock;
    }

    public final JsCompositeBlock getPrototypeBlock() {
        return this.prototypeBlock;
    }

    public final JsCompositeBlock getDeclarationsBlock() {
        return this.declarationsBlock;
    }
}
