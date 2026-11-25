package org.jetbrains.kotlin.js.translate.general;

import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameBinding;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;

/* JADX INFO: compiled from: normalizeImportTags.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"normalizeImportTags", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NormalizeImportTagsKt {
    public static final void normalizeImportTags(final JsProgramFragment jsProgramFragment) {
        Intrinsics.checkNotNullParameter(jsProgramFragment, "<this>");
        jsProgramFragment.getNameBindings().replaceAll(new UnaryOperator() { // from class: org.jetbrains.kotlin.js.translate.general.NormalizeImportTagsKt$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return NormalizeImportTagsKt.normalizeImportTags$lambda$2(jsProgramFragment, (JsNameBinding) obj);
            }
        });
        final Set set = SequencesKt.toSet(SequencesKt.map(CollectionsKt.asSequence(jsProgramFragment.getNameBindings()), new Function1<JsNameBinding, String>() { // from class: org.jetbrains.kotlin.js.translate.general.NormalizeImportTagsKt$normalizeImportTags$tagSet$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(JsNameBinding it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getKey();
            }
        }));
        CollectionsKt.retainAll(jsProgramFragment.getImports().entrySet(), new Function1<Map.Entry<String, JsExpression>, Boolean>() { // from class: org.jetbrains.kotlin.js.translate.general.NormalizeImportTagsKt.normalizeImportTags.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Map.Entry<String, JsExpression> entry) {
                Intrinsics.checkNotNullParameter(entry, "<name for destructuring parameter 0>");
                return Boolean.valueOf(set.contains(entry.getKey()));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final JsNameBinding normalizeImportTags$lambda$2(JsProgramFragment this_normalizeImportTags, JsNameBinding binding) {
        Intrinsics.checkNotNullParameter(this_normalizeImportTags, "$this_normalizeImportTags");
        Intrinsics.checkNotNullParameter(binding, "binding");
        String key = binding.getKey();
        JsName name2 = binding.getName();
        JsExpression jsExpression = this_normalizeImportTags.getImports().get(key);
        if (jsExpression == null) {
            return binding;
        }
        String strExtractImportTag = CollectUtilsKt.extractImportTag(new JsVars.JsVar(name2, this_normalizeImportTags.getImports().get(key)));
        JsNameBinding jsNameBinding = null;
        if (strExtractImportTag != null && !Intrinsics.areEqual(key, strExtractImportTag)) {
            this_normalizeImportTags.getImports().put(strExtractImportTag, jsExpression);
            jsNameBinding = new JsNameBinding(strExtractImportTag, name2);
        }
        return jsNameBinding == null ? binding : jsNameBinding;
    }
}
