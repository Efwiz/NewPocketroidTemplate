package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: TypeOfFIF.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {Namer.CREATE_KTYPE, "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeOfFIFKt {
    public static final JsExpression createKType(TranslationContext translationContext, KotlinType type) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return new KTypeConstructor(translationContext).createKType(type);
    }
}
