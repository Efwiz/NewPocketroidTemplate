package org.jetbrains.kotlin.name;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JsStandardClassIds.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\f\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0002¨\u0006\u0007"}, d2 = {"callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "", "packageName", "Lorg/jetbrains/kotlin/name/FqName;", "jsId", "Lorg/jetbrains/kotlin/name/ClassId;", "compiler.common.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsStandardClassIdsKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId jsId(String str) {
        return new ClassId(JsStandardClassIds.INSTANCE.getBASE_JS_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CallableId callableId(String str, FqName fqName) {
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return new CallableId(fqName, nameIdentifier);
    }
}
