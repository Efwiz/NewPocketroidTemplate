package org.jetbrains.kotlin.fir.resolve.calls.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.calls.ConeCallConflictResolverFactory;
import org.jetbrains.kotlin.fir.resolve.calls.ConeCompositeConflictResolver;
import org.jetbrains.kotlin.fir.resolve.calls.ConeIntegerOperatorConflictResolver;
import org.jetbrains.kotlin.fir.resolve.calls.ConeOverloadConflictResolver;
import org.jetbrains.kotlin.fir.resolve.inference.InferenceComponents;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator;
import org.jetbrains.kotlin.resolve.jvm.JvmTypeSpecificityComparator;

/* JADX INFO: compiled from: JvmCallConflictResolverFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/jvm/JvmCallConflictResolverFactory;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ConeCallConflictResolverFactory;", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/fir/resolve/calls/ConeCompositeConflictResolver;", "typeSpecificityComparator", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "components", "Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;", "transformerComponents", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmCallConflictResolverFactory extends ConeCallConflictResolverFactory {
    public static final JvmCallConflictResolverFactory INSTANCE = new JvmCallConflictResolverFactory();

    private JvmCallConflictResolverFactory() {
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ConeCallConflictResolverFactory
    public ConeCompositeConflictResolver create(TypeSpecificityComparator typeSpecificityComparator, InferenceComponents components, BodyResolveComponents transformerComponents) {
        Intrinsics.checkNotNullParameter(typeSpecificityComparator, "typeSpecificityComparator");
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(transformerComponents, "transformerComponents");
        JvmTypeSpecificityComparator jvmTypeSpecificityComparator = new JvmTypeSpecificityComparator(TypeComponentsKt.getTypeContext(components.getSession()));
        return new ConeCompositeConflictResolver(new ConeEquivalentCallConflictResolver(jvmTypeSpecificityComparator, components, transformerComponents), new JvmPlatformOverloadsConflictResolver(components.getSession()), ConeIntegerOperatorConflictResolver.INSTANCE, new ConeOverloadConflictResolver(jvmTypeSpecificityComparator, components, transformerComponents));
    }
}
