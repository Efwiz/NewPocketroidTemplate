package org.jetbrains.kotlin.js.coroutine;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.metadata.CoroutineMetadata;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;

/* JADX INFO: compiled from: CoroutineTransformationContext.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\"\u001a\u00020\b2\u0006\u0010#\u001a\u00020\bR\u001b\u0010\u0007\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u001a\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001b\u0010\u0019\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\f\u001a\u0004\b\u001a\u0010\nR\u001b\u0010\u001c\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\f\u001a\u0004\b\u001d\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/js/coroutine/CoroutineTransformationContext;", "", "scope", "Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsScope;Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;)V", "controllerFieldName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getControllerFieldName", "()Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "controllerFieldName$delegate", "Lkotlin/Lazy;", "entryBlock", "Lorg/jetbrains/kotlin/js/coroutine/CoroutineBlock;", "getEntryBlock", "()Lorg/jetbrains/kotlin/js/coroutine/CoroutineBlock;", "globalCatchBlock", "getGlobalCatchBlock", "localVariableNameCache", "", "metadata", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/CoroutineMetadata;", "getMetadata", "()Lorg/jetbrains/kotlin/js/backend/ast/metadata/CoroutineMetadata;", "receiverFieldName", "getReceiverFieldName", "receiverFieldName$delegate", "returnValueFieldName", "getReturnValueFieldName", "returnValueFieldName$delegate", "usedLocalVariableIds", "", "", "getFieldName", "variableName", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CoroutineTransformationContext {

    /* JADX INFO: renamed from: controllerFieldName$delegate, reason: from kotlin metadata */
    private final Lazy controllerFieldName;
    private final CoroutineBlock entryBlock;
    private final CoroutineBlock globalCatchBlock;
    private final Map<JsName, JsName> localVariableNameCache;
    private final CoroutineMetadata metadata;

    /* JADX INFO: renamed from: receiverFieldName$delegate, reason: from kotlin metadata */
    private final Lazy receiverFieldName;

    /* JADX INFO: renamed from: returnValueFieldName$delegate, reason: from kotlin metadata */
    private final Lazy returnValueFieldName;
    private final JsScope scope;
    private final Set<String> usedLocalVariableIds;

    public CoroutineTransformationContext(JsScope scope, JsFunction function) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(function, "function");
        this.scope = scope;
        this.localVariableNameCache = new LinkedHashMap();
        this.usedLocalVariableIds = new LinkedHashSet();
        this.entryBlock = new CoroutineBlock();
        this.globalCatchBlock = new CoroutineBlock();
        CoroutineMetadata coroutineMetadata = MetadataProperties.getCoroutineMetadata(function);
        Intrinsics.checkNotNull(coroutineMetadata);
        this.metadata = coroutineMetadata;
        this.controllerFieldName = LazyKt.lazy(new Function0<JsName>() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutineTransformationContext$controllerFieldName$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JsName invoke() {
                return this.this$0.scope.declareName("$controller");
            }
        });
        this.returnValueFieldName = LazyKt.lazy(new Function0<JsName>() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutineTransformationContext$returnValueFieldName$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JsName invoke() {
                return this.this$0.scope.declareName("$returnValue");
            }
        });
        this.receiverFieldName = LazyKt.lazy(new Function0<JsName>() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutineTransformationContext$receiverFieldName$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JsName invoke() {
                return this.this$0.scope.declareName("$this");
            }
        });
    }

    public final CoroutineBlock getEntryBlock() {
        return this.entryBlock;
    }

    public final CoroutineBlock getGlobalCatchBlock() {
        return this.globalCatchBlock;
    }

    public final CoroutineMetadata getMetadata() {
        return this.metadata;
    }

    public final JsName getControllerFieldName() {
        return (JsName) this.controllerFieldName.getValue();
    }

    public final JsName getReturnValueFieldName() {
        return (JsName) this.returnValueFieldName.getValue();
    }

    public final JsName getReceiverFieldName() {
        return (JsName) this.receiverFieldName.getValue();
    }

    public final JsName getFieldName(JsName variableName) {
        Intrinsics.checkNotNullParameter(variableName, "variableName");
        Map<JsName, JsName> map = this.localVariableNameCache;
        JsName jsNameDeclareName = map.get(variableName);
        if (jsNameDeclareName == null) {
            String str = "local$" + variableName.getIdent();
            int i = 0;
            String str2 = str;
            while (!this.usedLocalVariableIds.add(str2)) {
                str2 = str + '_' + i;
                i++;
            }
            jsNameDeclareName = this.scope.declareName(str2);
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareName, "declareName(...)");
            map.put(variableName, jsNameDeclareName);
        }
        return jsNameDeclareName;
    }
}
