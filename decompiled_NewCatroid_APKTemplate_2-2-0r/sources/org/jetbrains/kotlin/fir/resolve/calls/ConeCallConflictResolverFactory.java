package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.inference.InferenceComponents;
import org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator;

/* JADX INFO: compiled from: ConeCallConflictResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ConeCallConflictResolverFactory;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/fir/resolve/calls/ConeCallConflictResolver;", "typeSpecificityComparator", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "components", "Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;", "transformerComponents", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConeCallConflictResolverFactory implements FirSessionComponent {
    public abstract ConeCallConflictResolver create(TypeSpecificityComparator typeSpecificityComparator, InferenceComponents components, BodyResolveComponents transformerComponents);
}
