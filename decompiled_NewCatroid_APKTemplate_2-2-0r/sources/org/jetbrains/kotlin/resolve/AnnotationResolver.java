package org.jetbrains.kotlin.resolve;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: AnnotationResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&J&\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u0006H&J3\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u00112\u0011\u0010\u0014\u001a\r\u0012\u0004\u0012\u00020\u000f0\u0015¢\u0006\u0002\b\u00162\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0018H$J \u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0006H&J*\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J)\u0010\u001f\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u00112\u0011\u0010\u0014\u001a\r\u0012\u0004\u0012\u00020\u000f0\u0015¢\u0006\u0002\b\u00162\u0006\u0010\u0005\u001a\u00020\u0006J \u0010\u001f\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0005\u001a\u00020\u0006J)\u0010 \u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u00112\u0011\u0010\u0014\u001a\r\u0012\u0004\u0012\u00020\u000f0\u0015¢\u0006\u0002\b\u00162\u0006\u0010\u0005\u001a\u00020\u0006J \u0010 \u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/resolve/AnnotationResolver;", "", "()V", "getAnnotationArgumentValue", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "valueParameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "resolvedArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "resolveAnnotationCall", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadResolutionResults;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "annotationEntry", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "resolveAnnotationEntries", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "annotationEntries", "", "Lkotlin/jvm/JvmSuppressWildcards;", "shouldResolveArguments", "", "resolveAnnotationType", "Lorg/jetbrains/kotlin/types/KotlinType;", "entryElement", "resolveAnnotationsFromModifierList", "modifierList", "Lorg/jetbrains/kotlin/psi/KtModifierList;", "resolveAnnotationsWithArguments", "resolveAnnotationsWithoutArguments", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AnnotationResolver {
    public abstract ConstantValue<?> getAnnotationArgumentValue(BindingTrace trace, ValueParameterDescriptor valueParameter, ResolvedValueArgument resolvedArgument);

    public abstract OverloadResolutionResults<FunctionDescriptor> resolveAnnotationCall(KtAnnotationEntry annotationEntry, LexicalScope scope, BindingTrace trace);

    protected abstract Annotations resolveAnnotationEntries(LexicalScope scope, List<KtAnnotationEntry> annotationEntries, BindingTrace trace, boolean shouldResolveArguments);

    public abstract KotlinType resolveAnnotationType(LexicalScope scope, KtAnnotationEntry entryElement, BindingTrace trace);

    public final Annotations resolveAnnotationsWithoutArguments(LexicalScope scope, KtModifierList modifierList, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(trace, "trace");
        return resolveAnnotationsFromModifierList(scope, modifierList, trace, false);
    }

    public final Annotations resolveAnnotationsWithArguments(LexicalScope scope, KtModifierList modifierList, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(trace, "trace");
        return resolveAnnotationsFromModifierList(scope, modifierList, trace, true);
    }

    private final Annotations resolveAnnotationsFromModifierList(LexicalScope scope, KtModifierList modifierList, BindingTrace trace, boolean shouldResolveArguments) {
        if (modifierList == null) {
            return Annotations.INSTANCE.getEMPTY();
        }
        List<KtAnnotationEntry> annotationEntries = modifierList.getAnnotationEntries();
        Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
        return resolveAnnotationEntries(scope, annotationEntries, trace, shouldResolveArguments);
    }

    public final Annotations resolveAnnotationsWithoutArguments(LexicalScope scope, List<KtAnnotationEntry> annotationEntries, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(annotationEntries, "annotationEntries");
        Intrinsics.checkNotNullParameter(trace, "trace");
        return resolveAnnotationEntries(scope, annotationEntries, trace, false);
    }

    public final Annotations resolveAnnotationsWithArguments(LexicalScope scope, List<KtAnnotationEntry> annotationEntries, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(annotationEntries, "annotationEntries");
        Intrinsics.checkNotNullParameter(trace, "trace");
        return resolveAnnotationEntries(scope, annotationEntries, trace, true);
    }
}
