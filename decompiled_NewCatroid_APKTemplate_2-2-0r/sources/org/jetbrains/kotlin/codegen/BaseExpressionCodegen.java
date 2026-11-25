package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import org.jetbrains.kotlin.codegen.inline.NameGenerator;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeParametersUsages;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: BaseExpressionCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H&J\u0010\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u001cH&J\u0010\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\u001fH&R\u0016\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006 À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/BaseExpressionCodegen;", "", "frameMap", "Lorg/jetbrains/kotlin/codegen/FrameMapBase;", "getFrameMap", "()Lorg/jetbrains/kotlin/codegen/FrameMapBase;", "inlineNameGenerator", "Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;", "getInlineNameGenerator", "()Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;", "lastLineNumber", "", "getLastLineNumber", "()I", "typeSystem", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "getTypeSystem", "()Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "visitor", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "getVisitor", "()Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "consumeReifiedOperationMarker", "", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "markLineNumberAfterInlineIfNeeded", "registerLineNumberAfterwards", "", "propagateChildReifiedTypeParametersUsages", "reifiedTypeParametersUsages", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface BaseExpressionCodegen {
    void consumeReifiedOperationMarker(TypeParameterMarker typeParameter);

    FrameMapBase<?> getFrameMap();

    NameGenerator getInlineNameGenerator();

    int getLastLineNumber();

    TypeSystemCommonBackendContext getTypeSystem();

    InstructionAdapter getVisitor();

    void markLineNumberAfterInlineIfNeeded(boolean registerLineNumberAfterwards);

    void propagateChildReifiedTypeParametersUsages(ReifiedTypeParametersUsages reifiedTypeParametersUsages);
}
