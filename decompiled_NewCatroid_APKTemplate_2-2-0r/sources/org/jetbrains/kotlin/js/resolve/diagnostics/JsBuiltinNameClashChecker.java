package org.jetbrains.kotlin.js.resolve.diagnostics;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.js.naming.JsNameSuggestion;
import org.jetbrains.kotlin.js.naming.SuggestedName;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;

/* JADX INFO: compiled from: JsBuiltinNameClashChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \r2\u00020\u0001:\u0001\rB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsBuiltinNameClashChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "nameSuggestion", "Lorg/jetbrains/kotlin/js/naming/JsNameSuggestion;", "(Lorg/jetbrains/kotlin/js/naming/JsNameSuggestion;)V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "Companion", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsBuiltinNameClashChecker implements DeclarationChecker {
    private final JsNameSuggestion nameSuggestion;
    public static final Set<String> PROHIBITED_STATIC_NAMES = SetsKt.setOf((Object[]) new String[]{"prototype", "length", Namer.METADATA});
    public static final Set<String> PROHIBITED_MEMBER_NAMES = SetsKt.setOf(JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME);

    public JsBuiltinNameClashChecker(JsNameSuggestion nameSuggestion) {
        Intrinsics.checkNotNullParameter(nameSuggestion, "nameSuggestion");
        this.nameSuggestion = nameSuggestion;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!AnnotationsUtils.isNativeObject(descriptor) && (descriptor.getContainingDeclaration() instanceof ClassDescriptor)) {
            JsNameSuggestion jsNameSuggestion = this.nameSuggestion;
            BindingContext context2 = context.getTrace().getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
            SuggestedName suggestedNameSuggest = jsNameSuggestion.suggest(descriptor, context2);
            Intrinsics.checkNotNull(suggestedNameSuggest);
            if (suggestedNameSuggest.getStable()) {
                String str = (String) CollectionsKt.single((List) suggestedNameSuggest.getNames());
                if (descriptor instanceof ClassDescriptor) {
                    if (PROHIBITED_STATIC_NAMES.contains(str)) {
                        context.getTrace().report(ErrorsJs.JS_BUILTIN_NAME_CLASH.on(declaration, "Function." + str));
                    }
                } else if ((descriptor instanceof CallableMemberDescriptor) && PROHIBITED_MEMBER_NAMES.contains(str)) {
                    context.getTrace().report(ErrorsJs.JS_BUILTIN_NAME_CLASH.on(declaration, "Object.prototype." + str));
                }
            }
        }
    }
}
