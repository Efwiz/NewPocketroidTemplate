package org.jetbrains.kotlin.js.coroutine;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.js.backend.ast.JsDebugger;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperty;

/* JADX INFO: compiled from: CoroutineMetadataProperties.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0010\"?\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0001*\u00020\u00042\u000e\u0010\u0000\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00018F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\t\u0010\n\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\"/\u0010\u0003\u001a\u00020\u000b*\u00020\f2\u0006\u0010\u0000\u001a\u00020\u000b8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u000f\u0010\n\u001a\u0004\b\u0005\u0010\r\"\u0004\b\u0007\u0010\u000e\"3\u0010\u0010\u001a\u0004\u0018\u00010\u0002*\u00020\u00042\b\u0010\u0000\u001a\u0004\u0018\u00010\u00028F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0015\u0010\n\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\"/\u0010\u0010\u001a\u00020\u000b*\u00020\f2\u0006\u0010\u0000\u001a\u00020\u000b8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0016\u0010\n\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0013\u0010\u000e\"3\u0010\u0017\u001a\u0004\u0018\u00010\u0002*\u00020\u00042\b\u0010\u0000\u001a\u0004\u0018\u00010\u00028F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u001a\u0010\n\u001a\u0004\b\u0018\u0010\u0012\"\u0004\b\u0019\u0010\u0014\"/\u0010\u0017\u001a\u00020\u000b*\u00020\f2\u0006\u0010\u0000\u001a\u00020\u000b8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u001b\u0010\n\u001a\u0004\b\u0018\u0010\r\"\u0004\b\u0019\u0010\u000e¨\u0006\u001c"}, d2 = {"<set-?>", "", "Lorg/jetbrains/kotlin/js/coroutine/CoroutineBlock;", "finallyPath", "Lorg/jetbrains/kotlin/js/backend/ast/JsDebugger;", "getFinallyPath", "(Lorg/jetbrains/kotlin/js/backend/ast/JsDebugger;)Ljava/util/List;", "setFinallyPath", "(Lorg/jetbrains/kotlin/js/backend/ast/JsDebugger;Ljava/util/List;)V", "finallyPath$delegate", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/MetadataProperty;", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;)Z", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;Z)V", "finallyPath$delegate$1", "targetBlock", "getTargetBlock", "(Lorg/jetbrains/kotlin/js/backend/ast/JsDebugger;)Lorg/jetbrains/kotlin/js/coroutine/CoroutineBlock;", "setTargetBlock", "(Lorg/jetbrains/kotlin/js/backend/ast/JsDebugger;Lorg/jetbrains/kotlin/js/coroutine/CoroutineBlock;)V", "targetBlock$delegate", "targetBlock$delegate$1", "targetExceptionBlock", "getTargetExceptionBlock", "setTargetExceptionBlock", "targetExceptionBlock$delegate", "targetExceptionBlock$delegate$1", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CoroutineMetadataPropertiesKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(CoroutineMetadataPropertiesKt.class, "js.translator"), "targetBlock", "getTargetBlock(Lorg/jetbrains/kotlin/js/backend/ast/JsDebugger;)Lorg/jetbrains/kotlin/js/coroutine/CoroutineBlock;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(CoroutineMetadataPropertiesKt.class, "js.translator"), "targetExceptionBlock", "getTargetExceptionBlock(Lorg/jetbrains/kotlin/js/backend/ast/JsDebugger;)Lorg/jetbrains/kotlin/js/coroutine/CoroutineBlock;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(CoroutineMetadataPropertiesKt.class, "js.translator"), "finallyPath", "getFinallyPath(Lorg/jetbrains/kotlin/js/backend/ast/JsDebugger;)Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(CoroutineMetadataPropertiesKt.class, "js.translator"), "targetBlock", "getTargetBlock(Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;)Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(CoroutineMetadataPropertiesKt.class, "js.translator"), "targetExceptionBlock", "getTargetExceptionBlock(Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;)Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(CoroutineMetadataPropertiesKt.class, "js.translator"), "finallyPath", "getFinallyPath(Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;)Z"))};
    private static final MetadataProperty targetBlock$delegate = new MetadataProperty(null);
    private static final MetadataProperty targetExceptionBlock$delegate = new MetadataProperty(null);
    private static final MetadataProperty finallyPath$delegate = new MetadataProperty(null);
    private static final MetadataProperty targetBlock$delegate$1 = new MetadataProperty(false);
    private static final MetadataProperty targetExceptionBlock$delegate$1 = new MetadataProperty(false);
    private static final MetadataProperty finallyPath$delegate$1 = new MetadataProperty(false);

    public static final CoroutineBlock getTargetBlock(JsDebugger jsDebugger) {
        Intrinsics.checkNotNullParameter(jsDebugger, "<this>");
        return (CoroutineBlock) targetBlock$delegate.getValue(jsDebugger, $$delegatedProperties[0]);
    }

    public static final void setTargetBlock(JsDebugger jsDebugger, CoroutineBlock coroutineBlock) {
        Intrinsics.checkNotNullParameter(jsDebugger, "<this>");
        targetBlock$delegate.setValue(jsDebugger, $$delegatedProperties[0], coroutineBlock);
    }

    public static final CoroutineBlock getTargetExceptionBlock(JsDebugger jsDebugger) {
        Intrinsics.checkNotNullParameter(jsDebugger, "<this>");
        return (CoroutineBlock) targetExceptionBlock$delegate.getValue(jsDebugger, $$delegatedProperties[1]);
    }

    public static final void setTargetExceptionBlock(JsDebugger jsDebugger, CoroutineBlock coroutineBlock) {
        Intrinsics.checkNotNullParameter(jsDebugger, "<this>");
        targetExceptionBlock$delegate.setValue(jsDebugger, $$delegatedProperties[1], coroutineBlock);
    }

    public static final List<CoroutineBlock> getFinallyPath(JsDebugger jsDebugger) {
        Intrinsics.checkNotNullParameter(jsDebugger, "<this>");
        return (List) finallyPath$delegate.getValue(jsDebugger, $$delegatedProperties[2]);
    }

    public static final void setFinallyPath(JsDebugger jsDebugger, List<CoroutineBlock> list) {
        Intrinsics.checkNotNullParameter(jsDebugger, "<this>");
        finallyPath$delegate.setValue(jsDebugger, $$delegatedProperties[2], list);
    }

    public static final boolean getTargetBlock(JsExpressionStatement jsExpressionStatement) {
        Intrinsics.checkNotNullParameter(jsExpressionStatement, "<this>");
        return ((Boolean) targetBlock$delegate$1.getValue(jsExpressionStatement, $$delegatedProperties[3])).booleanValue();
    }

    public static final void setTargetBlock(JsExpressionStatement jsExpressionStatement, boolean z) {
        Intrinsics.checkNotNullParameter(jsExpressionStatement, "<this>");
        targetBlock$delegate$1.setValue(jsExpressionStatement, $$delegatedProperties[3], Boolean.valueOf(z));
    }

    public static final boolean getTargetExceptionBlock(JsExpressionStatement jsExpressionStatement) {
        Intrinsics.checkNotNullParameter(jsExpressionStatement, "<this>");
        return ((Boolean) targetExceptionBlock$delegate$1.getValue(jsExpressionStatement, $$delegatedProperties[4])).booleanValue();
    }

    public static final void setTargetExceptionBlock(JsExpressionStatement jsExpressionStatement, boolean z) {
        Intrinsics.checkNotNullParameter(jsExpressionStatement, "<this>");
        targetExceptionBlock$delegate$1.setValue(jsExpressionStatement, $$delegatedProperties[4], Boolean.valueOf(z));
    }

    public static final boolean getFinallyPath(JsExpressionStatement jsExpressionStatement) {
        Intrinsics.checkNotNullParameter(jsExpressionStatement, "<this>");
        return ((Boolean) finallyPath$delegate$1.getValue(jsExpressionStatement, $$delegatedProperties[5])).booleanValue();
    }

    public static final void setFinallyPath(JsExpressionStatement jsExpressionStatement, boolean z) {
        Intrinsics.checkNotNullParameter(jsExpressionStatement, "<this>");
        finallyPath$delegate$1.setValue(jsExpressionStatement, $$delegatedProperties[5], Boolean.valueOf(z));
    }
}
