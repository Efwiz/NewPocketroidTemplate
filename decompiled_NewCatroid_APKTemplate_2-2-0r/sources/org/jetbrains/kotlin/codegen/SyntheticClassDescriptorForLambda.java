package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.impl.ClassDescriptorImpl;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElementKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: SyntheticClassDescriptorForLambda.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0006\u0010\f\u001a\u00020\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/SyntheticClassDescriptorForLambda;", "Lorg/jetbrains/kotlin/descriptors/impl/ClassDescriptorImpl;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "supertypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/name/Name;Ljava/util/Collection;Lorg/jetbrains/kotlin/psi/KtElement;)V", "isCallableReference", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SyntheticClassDescriptorForLambda extends ClassDescriptorImpl {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SyntheticClassDescriptorForLambda(DeclarationDescriptor containingDeclaration, Name name2, Collection<? extends KotlinType> supertypes, KtElement element) {
        super(containingDeclaration, name2, Modality.FINAL, ClassKind.CLASS, supertypes, KotlinSourceElementKt.toSourceElement(element), false, LockBasedStorageManager.NO_LOCKS);
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(supertypes, "supertypes");
        Intrinsics.checkNotNullParameter(element, "element");
        initialize(MemberScope.Empty.INSTANCE, SetsKt.emptySet(), null);
    }

    public final boolean isCallableReference() {
        SourceElement source = getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        return PsiSourceElementKt.getPsi(source) instanceof KtCallableReferenceExpression;
    }
}
