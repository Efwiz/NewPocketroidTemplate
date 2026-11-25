package org.jetbrains.kotlin.resolve.calls.inference;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzerContext;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.checkers.EmptyIntersectionTypeInfo;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: NewConstraintSystem.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&J\b\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\u0012H&J\u0018\u0010\u0013\u001a\u0004\u0018\u00010\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H&J\b\u0010\u0018\u001a\u00020\u0019H&R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001aÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;", "", "errors", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "getErrors", "()Ljava/util/List;", "hasContradiction", "", "getHasContradiction", "()Z", "asConstraintSystemCompleterContext", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionContext;", "asPostponedArgumentsAnalyzerContext", "Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzerContext;", "asReadOnlyStorage", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "getBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "getEmptyIntersectionTypeKind", "Lorg/jetbrains/kotlin/resolve/checkers/EmptyIntersectionTypeInfo;", SpecTypePair.NAME_types, "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "resolveForkPointsConstraints", "", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface NewConstraintSystem {
    ConstraintSystemCompletionContext asConstraintSystemCompleterContext();

    PostponedArgumentsAnalyzerContext asPostponedArgumentsAnalyzerContext();

    ConstraintStorage asReadOnlyStorage();

    ConstraintSystemBuilder getBuilder();

    EmptyIntersectionTypeInfo getEmptyIntersectionTypeKind(Collection<? extends KotlinTypeMarker> types);

    List<ConstraintSystemError> getErrors();

    boolean getHasContradiction();

    void resolveForkPointsConstraints();
}
