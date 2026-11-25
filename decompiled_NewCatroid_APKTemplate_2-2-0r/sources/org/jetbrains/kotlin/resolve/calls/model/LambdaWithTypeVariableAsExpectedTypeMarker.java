package org.jetbrains.kotlin.resolve.calls.model;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: PostponedResolvedAtomMarker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0007\u001a\u00020\b2\u0010\u0010\t\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003H&R\u001c\u0010\u0002\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/LambdaWithTypeVariableAsExpectedTypeMarker;", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedAtomWithRevisableExpectedType;", "parameterTypesFromDeclaration", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getParameterTypesFromDeclaration", "()Ljava/util/List;", "updateParameterTypesFromDeclaration", "", SpecTypePair.NAME_types, "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LambdaWithTypeVariableAsExpectedTypeMarker extends PostponedAtomWithRevisableExpectedType {
    List<KotlinTypeMarker> getParameterTypesFromDeclaration();

    void updateParameterTypesFromDeclaration(List<? extends KotlinTypeMarker> types);
}
