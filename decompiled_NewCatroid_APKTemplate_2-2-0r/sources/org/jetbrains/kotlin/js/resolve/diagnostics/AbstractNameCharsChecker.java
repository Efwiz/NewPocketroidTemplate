package org.jetbrains.kotlin.js.resolve.diagnostics;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.js.naming.NameSuggestion;
import org.jetbrains.kotlin.js.naming.SuggestedName;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;

/* JADX INFO: compiled from: JsNameCharsChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/AbstractNameCharsChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "suggestion", "Lorg/jetbrains/kotlin/js/naming/NameSuggestion;", "(Lorg/jetbrains/kotlin/js/naming/NameSuggestion;)V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractNameCharsChecker implements DeclarationChecker {
    private final NameSuggestion suggestion;

    public AbstractNameCharsChecker(NameSuggestion suggestion) {
        Intrinsics.checkNotNullParameter(suggestion, "suggestion");
        this.suggestion = suggestion;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        SuggestedName suggestedNameSuggest;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.JsAllowInvalidCharsIdentifiersEscaping)) {
            return;
        }
        BindingContext bindingContext = context.getTrace().getBindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        if ((descriptor instanceof PropertyAccessorDescriptor) && AnnotationsUtils.getJsName(descriptor) == null) {
            return;
        }
        if (((descriptor instanceof ConstructorDescriptor) && AnnotationsUtils.getJsName(descriptor) == null && AnnotationsUtils.isExportedObject(descriptor, bindingContext)) || (suggestedNameSuggest = this.suggestion.suggest(descriptor, bindingContext)) == null || !suggestedNameSuggest.getStable()) {
            return;
        }
        List<String> names = suggestedNameSuggest.getNames();
        if ((names instanceof Collection) && names.isEmpty()) {
            return;
        }
        for (String str : names) {
            if (!Intrinsics.areEqual(NameSuggestion.INSTANCE.sanitizeName(str), str)) {
                context.getTrace().report(ErrorsJs.NAME_CONTAINS_ILLEGAL_CHARS.on(declaration));
                return;
            }
        }
    }
}
