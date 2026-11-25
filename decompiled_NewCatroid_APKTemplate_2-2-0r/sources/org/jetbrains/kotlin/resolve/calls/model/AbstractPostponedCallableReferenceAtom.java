package org.jetbrains.kotlin.resolve.calls.model;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.resolve.calls.components.CallableReferenceResolutionKt;
import org.jetbrains.kotlin.resolve.calls.components.InputOutputTypes;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0019\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r\u0082\u0001\u0002\u000e\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/AbstractPostponedCallableReferenceAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallableReferenceArgumentAtom;", "atom", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "(Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;Lorg/jetbrains/kotlin/types/UnwrappedType;)V", "inputTypes", "", "getInputTypes", "()Ljava/util/Collection;", "outputType", "getOutputType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceWithRevisedExpectedTypeAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedCallableReferenceAtom;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractPostponedCallableReferenceAtom extends ResolvedCallableReferenceArgumentAtom {
    public /* synthetic */ AbstractPostponedCallableReferenceAtom(CallableReferenceKotlinCallArgument callableReferenceKotlinCallArgument, UnwrappedType unwrappedType, DefaultConstructorMarker defaultConstructorMarker) {
        this(callableReferenceKotlinCallArgument, unwrappedType);
    }

    private AbstractPostponedCallableReferenceAtom(CallableReferenceKotlinCallArgument callableReferenceKotlinCallArgument, UnwrappedType unwrappedType) {
        super(callableReferenceKotlinCallArgument, unwrappedType);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtom, org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    public Collection<UnwrappedType> getInputTypes() {
        List<UnwrappedType> listListOfNotNull;
        InputOutputTypes inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType = CallableReferenceResolutionKt.extractInputOutputTypesFromCallableReferenceExpectedType(getExpectedType());
        if (inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType == null || (listListOfNotNull = inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType.getInputTypes()) == null) {
            listListOfNotNull = CollectionsKt.listOfNotNull(getExpectedType());
        }
        return listListOfNotNull;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    /* JADX INFO: renamed from: getOutputType */
    public UnwrappedType getReturnType() {
        InputOutputTypes inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType = CallableReferenceResolutionKt.extractInputOutputTypesFromCallableReferenceExpectedType(getExpectedType());
        if (inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType != null) {
            return inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType.getOutputType();
        }
        return null;
    }
}
