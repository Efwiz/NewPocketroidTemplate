package org.jetbrains.kotlin.types.model;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: TypeSystemContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0003H&JB\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00112\u0010\b\u0002\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u000eH&J\u0010\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0017H&J\u0018\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u001bH&J\u0010\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001dÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/types/model/TypeSystemTypeFactoryContext;", "Lorg/jetbrains/kotlin/types/model/TypeSystemBuiltInsContext;", "createErrorType", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "debugName", "", "createFlexibleType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "lowerBound", "upperBound", "createSimpleType", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "arguments", "", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "nullable", "", "isExtensionFunction", "attributes", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "createStarProjection", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "createTypeArgument", ModuleXmlParser.TYPE, "variance", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "createUninferredType", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeSystemTypeFactoryContext extends TypeSystemBuiltInsContext {
    SimpleTypeMarker createErrorType(String debugName);

    KotlinTypeMarker createFlexibleType(SimpleTypeMarker lowerBound, SimpleTypeMarker upperBound);

    SimpleTypeMarker createSimpleType(TypeConstructorMarker constructor, List<? extends TypeArgumentMarker> arguments, boolean nullable, boolean isExtensionFunction, List<? extends AnnotationMarker> attributes);

    TypeArgumentMarker createStarProjection(TypeParameterMarker typeParameter);

    TypeArgumentMarker createTypeArgument(KotlinTypeMarker type, TypeVariance variance);

    KotlinTypeMarker createUninferredType(TypeConstructorMarker constructor);

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ SimpleTypeMarker createSimpleType$default(TypeSystemTypeFactoryContext typeSystemTypeFactoryContext, TypeConstructorMarker typeConstructorMarker, List list, boolean z, boolean z2, List list2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createSimpleType");
        }
        if ((i & 8) != 0) {
            z2 = false;
        }
        boolean z3 = z2;
        if ((i & 16) != 0) {
            list2 = null;
        }
        return typeSystemTypeFactoryContext.createSimpleType(typeConstructorMarker, list, z, z3, list2);
    }
}
