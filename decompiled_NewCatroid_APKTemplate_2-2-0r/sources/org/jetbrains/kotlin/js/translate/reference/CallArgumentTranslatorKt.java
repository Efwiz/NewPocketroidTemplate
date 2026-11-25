package org.jetbrains.kotlin.js.translate.reference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.expression.PatternTranslator;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: CallArgumentTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"buildReifiedTypeArgs", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/types/KotlinType;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CallArgumentTranslatorKt {
    public static final List<JsExpression> buildReifiedTypeArgs(Map<TypeParameterDescriptor, ? extends KotlinType> map, TranslationContext context) {
        KotlinType kotlinType;
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        ArrayList arrayList = new ArrayList();
        PatternTranslator patternTranslatorNewInstance = PatternTranslator.newInstance(context);
        Intrinsics.checkNotNullExpressionValue(patternTranslatorNewInstance, "newInstance(...)");
        for (TypeParameterDescriptor typeParameterDescriptor : CollectionsKt.sortedWith(map.keySet(), new Comparator() { // from class: org.jetbrains.kotlin.js.translate.reference.CallArgumentTranslatorKt$buildReifiedTypeArgs$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(Integer.valueOf(((TypeParameterDescriptor) t).getIndex()), Integer.valueOf(((TypeParameterDescriptor) t2).getIndex()));
            }
        })) {
            if (typeParameterDescriptor.isReified() && (kotlinType = map.get(typeParameterDescriptor)) != null) {
                arrayList.add(UtilsKt.getReferenceToJsClass(kotlinType, context));
                JsExpression isTypeCheckCallable = patternTranslatorNewInstance.getIsTypeCheckCallable(kotlinType);
                Intrinsics.checkNotNullExpressionValue(isTypeCheckCallable, "getIsTypeCheckCallable(...)");
                arrayList.add(isTypeCheckCallable);
            }
        }
        return arrayList;
    }
}
