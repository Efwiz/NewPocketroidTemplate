package org.jetbrains.kotlin.js.translate.intrinsic.objects;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: objectsIntrinsics.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/objects/DefaultClassObjectIntrinsic;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/objects/ObjectIntrinsic;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/name/FqName;)V", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "apply", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultClassObjectIntrinsic implements ObjectIntrinsic {
    private final FqName fqName;

    public DefaultClassObjectIntrinsic(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        this.fqName = fqName;
    }

    public final FqName getFqName() {
        return this.fqName;
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.objects.ObjectIntrinsic
    public JsExpression apply(TranslationContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        JsExpression referenceToIntrinsic = context.getReferenceToIntrinsic(this.fqName.asString());
        Intrinsics.checkNotNullExpressionValue(referenceToIntrinsic, "getReferenceToIntrinsic(...)");
        return referenceToIntrinsic;
    }
}
