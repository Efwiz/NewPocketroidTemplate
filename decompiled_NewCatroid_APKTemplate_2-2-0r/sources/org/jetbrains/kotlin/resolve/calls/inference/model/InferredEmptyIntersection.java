package org.jetbrains.kotlin.resolve.calls.inference.model;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.EmptyIntersectionTypeKind;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: ConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\u0082\u0001\u0002\u0011\u0012ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0013À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/InferredEmptyIntersection;", "", "causingTypes", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getCausingTypes", "()Ljava/util/List;", "incompatibleTypes", "getIncompatibleTypes", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/types/EmptyIntersectionTypeKind;", "getKind", "()Lorg/jetbrains/kotlin/types/EmptyIntersectionTypeKind;", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "getTypeVariable", "()Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/InferredEmptyIntersectionError;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/InferredEmptyIntersectionWarning;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface InferredEmptyIntersection {
    List<KotlinTypeMarker> getCausingTypes();

    List<KotlinTypeMarker> getIncompatibleTypes();

    EmptyIntersectionTypeKind getKind();

    TypeVariableMarker getTypeVariable();
}
