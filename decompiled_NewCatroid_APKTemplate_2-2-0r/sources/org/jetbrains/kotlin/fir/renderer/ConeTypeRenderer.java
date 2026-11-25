package org.jetbrains.kotlin.fir.renderer;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributes;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributesKt;
import org.jetbrains.kotlin.fir.types.ConeAttribute;
import org.jetbrains.kotlin.fir.types.ConeCapturedType;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeIntegerConstantOperatorType;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralConstantType;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeConflictingProjection;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionIn;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionOut;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeStubType;
import org.jetbrains.kotlin.fir.types.ConeStubTypeForChainInference;
import org.jetbrains.kotlin.fir.types.ConeStubTypeForSyntheticFixation;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableType;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ConeTypeRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0014J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001bH\u0014J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001cJA\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001c2\u0014\u0010\u001e\u001a\u0010\u0012\u0004\u0012\u00020\u001c\u0012\u0006\u0012\u0004\u0018\u00010 0\u001f2\u0019\b\u0002\u0010!\u001a\u0013\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u00170\u001f¢\u0006\u0002\b#H\u0016J\f\u0010\u0016\u001a\u00020\u0017*\u00020$H\u0002J\f\u0010\u0016\u001a\u00020\u0017*\u00020\"H\u0002J\f\u0010%\u001a\u00020\u0017*\u00020\u001cH\u0002J\f\u0010&\u001a\u00020'*\u00020\u0019H\u0002J\f\u0010(\u001a\u00020\u0017*\u00020\u001cH\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00060\nj\u0002`\u000bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/ConeTypeRenderer;", "", "()V", "attributeRenderer", "Lorg/jetbrains/kotlin/fir/renderer/ConeAttributeRenderer;", "getAttributeRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/ConeAttributeRenderer;", "setAttributeRenderer", "(Lorg/jetbrains/kotlin/fir/renderer/ConeAttributeRenderer;)V", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "getBuilder", "()Ljava/lang/StringBuilder;", "setBuilder", "(Ljava/lang/StringBuilder;)V", "idRenderer", "Lorg/jetbrains/kotlin/fir/renderer/ConeIdRenderer;", "getIdRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/ConeIdRenderer;", "setIdRenderer", "(Lorg/jetbrains/kotlin/fir/renderer/ConeIdRenderer;)V", "render", "", "flexibleType", "Lorg/jetbrains/kotlin/fir/types/ConeFlexibleType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeIntegerLiteralType;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "renderAsPossibleFunctionType", "functionClassKindExtractor", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "renderType", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "Lkotlin/ExtensionFunctionType;", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "renderAttributes", "renderForSameLookupTags", "", "renderNonCompilerAttributes", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ConeTypeRenderer {
    private ConeAttributeRenderer attributeRenderer = ConeAttributeRenderer.ToString.INSTANCE;
    public StringBuilder builder;
    public ConeIdRenderer idRenderer;

    public final StringBuilder getBuilder() {
        StringBuilder sb = this.builder;
        if (sb != null) {
            return sb;
        }
        Intrinsics.throwUninitializedPropertyAccessException("builder");
        return null;
    }

    public final void setBuilder(StringBuilder sb) {
        Intrinsics.checkNotNullParameter(sb, "<set-?>");
        this.builder = sb;
    }

    public final ConeIdRenderer getIdRenderer() {
        ConeIdRenderer coneIdRenderer = this.idRenderer;
        if (coneIdRenderer != null) {
            return coneIdRenderer;
        }
        Intrinsics.throwUninitializedPropertyAccessException("idRenderer");
        return null;
    }

    public final void setIdRenderer(ConeIdRenderer coneIdRenderer) {
        Intrinsics.checkNotNullParameter(coneIdRenderer, "<set-?>");
        this.idRenderer = coneIdRenderer;
    }

    public final ConeAttributeRenderer getAttributeRenderer() {
        return this.attributeRenderer;
    }

    public final void setAttributeRenderer(ConeAttributeRenderer coneAttributeRenderer) {
        Intrinsics.checkNotNullParameter(coneAttributeRenderer, "<set-?>");
        this.attributeRenderer = coneAttributeRenderer;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void renderAsPossibleFunctionType$default(ConeTypeRenderer coneTypeRenderer, ConeKotlinType coneKotlinType, Function1 function1, Function1 function2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: renderAsPossibleFunctionType");
        }
        if ((i & 4) != 0) {
            function2 = new Function1<ConeTypeProjection, Unit>() { // from class: org.jetbrains.kotlin.fir.renderer.ConeTypeRenderer.renderAsPossibleFunctionType.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ConeTypeProjection coneTypeProjection) {
                    invoke2(coneTypeProjection);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ConeTypeProjection coneTypeProjection) {
                    Intrinsics.checkNotNullParameter(coneTypeProjection, "$this$null");
                    ConeTypeRenderer.this.render(coneTypeProjection);
                }
            };
        }
        coneTypeRenderer.renderAsPossibleFunctionType(coneKotlinType, function1, function2);
    }

    public void renderAsPossibleFunctionType(ConeKotlinType type, Function1<? super ConeKotlinType, ? extends FunctionTypeKind> functionClassKindExtractor, Function1<? super ConeTypeProjection, Unit> renderType) {
        Pair pair;
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(functionClassKindExtractor, "functionClassKindExtractor");
        Intrinsics.checkNotNullParameter(renderType, "renderType");
        FunctionTypeKind functionTypeKindInvoke = functionClassKindExtractor.invoke(type);
        if (functionTypeKindInvoke == null || functionTypeKindInvoke.getIsReflectType()) {
            renderType.invoke(type);
            return;
        }
        renderNonCompilerAttributes(type);
        if (ConeTypeUtilsKt.isMarkedNullable(type)) {
            getBuilder().append("(");
        }
        String prefixForTypeRender = functionTypeKindInvoke.getPrefixForTypeRender();
        if (prefixForTypeRender != null) {
            getBuilder().append(prefixForTypeRender);
            getBuilder().append(" ");
        }
        ConeTypeProjection[] typeArguments = type.getTypeArguments();
        if (CompilerConeAttributesKt.isExtensionFunctionType(type) && !Intrinsics.areEqual(ArraysKt.first(typeArguments), ConeStarProjection.INSTANCE)) {
            pair = TuplesKt.to(ArraysKt.first(typeArguments), ArraysKt.drop(typeArguments, 1));
        } else {
            pair = TuplesKt.to(null, ArraysKt.toList(typeArguments));
        }
        ConeTypeProjection coneTypeProjection = (ConeTypeProjection) pair.component1();
        List list = (List) pair.component2();
        int i = 0;
        List<ConeTypeProjection> listSubList = list.subList(0, list.size() - 1);
        ConeTypeProjection coneTypeProjection2 = (ConeTypeProjection) CollectionsKt.last(list);
        if (coneTypeProjection != null) {
            render(coneTypeProjection);
            getBuilder().append(".");
        }
        getBuilder().append("(");
        for (ConeTypeProjection coneTypeProjection3 : listSubList) {
            int i2 = i + 1;
            if (i != 0) {
                getBuilder().append(", ");
            }
            render(coneTypeProjection3);
            i = i2;
        }
        getBuilder().append(") -> ");
        render(coneTypeProjection2);
        if (ConeTypeUtilsKt.isMarkedNullable(type)) {
            getBuilder().append(")?");
        }
    }

    public final void render(ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        boolean z = type instanceof ConeFlexibleType;
        if (!z && !(type instanceof ConeDefinitelyNotNullType)) {
            renderAttributes(type);
        }
        if (type instanceof ConeTypeVariableType) {
            getBuilder().append("TypeVariable(");
            getBuilder().append(((ConeTypeVariableType) type).getLookupTag().getName());
            getBuilder().append(Constant.AFTER_QUTO);
        } else if (type instanceof ConeDefinitelyNotNullType) {
            render((ConeKotlinType) ((ConeDefinitelyNotNullType) type).getOriginal());
            getBuilder().append(" & Any");
        } else if (type instanceof ConeErrorType) {
            getBuilder().append("ERROR CLASS: " + ((ConeErrorType) type).getDiagnostic().getReason());
        } else if (type instanceof ConeCapturedType) {
            getBuilder().append("CapturedType(");
            render(((ConeCapturedType) type).getConstructor().getProjection());
            getBuilder().append(Constant.AFTER_QUTO);
        } else if (type instanceof ConeClassLikeType) {
            render((ConeClassLikeType) type);
        } else if (type instanceof ConeLookupTagBasedType) {
            getBuilder().append(((ConeLookupTagBasedType) type).getLookupTag().getName().asString());
        } else if (type instanceof ConeDynamicType) {
            getBuilder().append("dynamic");
        } else if (z) {
            render((ConeFlexibleType) type);
        } else if (type instanceof ConeIntersectionType) {
            getBuilder().append("it(");
            int i = 0;
            for (ConeKotlinType coneKotlinType : ((ConeIntersectionType) type).getIntersectedTypes()) {
                int i2 = i + 1;
                if (i > 0) {
                    getBuilder().append(" & ");
                }
                render(coneKotlinType);
                i = i2;
            }
            getBuilder().append(Constant.AFTER_QUTO);
        } else if (type instanceof ConeStubTypeForSyntheticFixation) {
            getBuilder().append("Stub (fixation): " + ((ConeStubTypeForSyntheticFixation) type).getConstructor().getVariable());
        } else if (type instanceof ConeStubTypeForChainInference) {
            getBuilder().append("Stub (chain inference): " + ((ConeStubTypeForChainInference) type).getConstructor().getVariable());
        } else if (type instanceof ConeStubType) {
            getBuilder().append("Stub (subtyping): " + ((ConeStubType) type).getConstructor().getVariable());
        } else if (type instanceof ConeIntegerLiteralType) {
            render((ConeIntegerLiteralType) type);
        }
        if (z || (type instanceof ConeErrorType)) {
            return;
        }
        getBuilder().append(type.getNullability().getSuffix());
    }

    private final void render(ConeClassLikeType coneClassLikeType) {
        getIdRenderer().renderClassId(coneClassLikeType.getLookupTag().getClassId());
        if (coneClassLikeType.getTypeArguments().length == 0) {
            return;
        }
        getBuilder().append("<");
        ConeTypeProjection[] typeArguments = coneClassLikeType.getTypeArguments();
        int length = typeArguments.length;
        for (int i = 0; i < length; i++) {
            ConeTypeProjection coneTypeProjection = typeArguments[i];
            if (i > 0) {
                getBuilder().append(", ");
            }
            render(coneTypeProjection);
        }
        getBuilder().append(">");
    }

    private final boolean renderForSameLookupTags(ConeFlexibleType coneFlexibleType) {
        if (!(coneFlexibleType.getLowerBound() instanceof ConeLookupTagBasedType) || !(coneFlexibleType.getUpperBound() instanceof ConeLookupTagBasedType) || !Intrinsics.areEqual(((ConeLookupTagBasedType) coneFlexibleType.getLowerBound()).getLookupTag(), ((ConeLookupTagBasedType) coneFlexibleType.getUpperBound()).getLookupTag()) || coneFlexibleType.getLowerBound().getNullability() != ConeNullability.NOT_NULL || coneFlexibleType.getUpperBound().getNullability() != ConeNullability.NULLABLE) {
            return false;
        }
        if ((coneFlexibleType.getLowerBound() instanceof ConeClassLikeType) && coneFlexibleType.getLowerBound().getTypeArguments().length != 0) {
            return false;
        }
        if ((coneFlexibleType.getUpperBound() instanceof ConeClassLikeType) && coneFlexibleType.getUpperBound().getTypeArguments().length != 0) {
            return false;
        }
        render((ConeKotlinType) coneFlexibleType.getLowerBound());
        getBuilder().append("!");
        return true;
    }

    protected void render(ConeFlexibleType flexibleType) {
        Intrinsics.checkNotNullParameter(flexibleType, "flexibleType");
        if (renderForSameLookupTags(flexibleType)) {
            return;
        }
        getBuilder().append("ft<");
        render((ConeKotlinType) flexibleType.getLowerBound());
        getBuilder().append(", ");
        render((ConeKotlinType) flexibleType.getUpperBound());
        getBuilder().append(">");
    }

    private final void renderAttributes(ConeKotlinType coneKotlinType) {
        if (CollectionsKt.any(coneKotlinType.getAttributes())) {
            getBuilder().append(this.attributeRenderer.render(coneKotlinType.getAttributes()));
        }
    }

    private final void renderNonCompilerAttributes(ConeKotlinType coneKotlinType) {
        Map<KClass<? extends ConeAttribute<?>>, ClassId> classIdByCompilerAttributeKey = CompilerConeAttributes.INSTANCE.getClassIdByCompilerAttributeKey();
        Iterable attributes = coneKotlinType.getAttributes();
        if ((attributes instanceof Collection) && ((Collection) attributes).isEmpty()) {
            return;
        }
        Iterator it = attributes.iterator();
        while (it.hasNext()) {
            if (!classIdByCompilerAttributeKey.containsKey(((ConeAttribute) it.next()).getKey())) {
                getBuilder().append(this.attributeRenderer.render(coneKotlinType.getAttributes()));
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void render(ConeTypeProjection coneTypeProjection) {
        if (Intrinsics.areEqual(coneTypeProjection, ConeStarProjection.INSTANCE)) {
            getBuilder().append("*");
            return;
        }
        if (coneTypeProjection instanceof ConeKotlinTypeConflictingProjection) {
            getBuilder().append("CONFLICTING-PROJECTION ");
            render(((ConeKotlinTypeConflictingProjection) coneTypeProjection).getType());
            return;
        }
        if (coneTypeProjection instanceof ConeKotlinTypeProjectionIn) {
            getBuilder().append("in ");
            render(((ConeKotlinTypeProjectionIn) coneTypeProjection).getType());
        } else if (coneTypeProjection instanceof ConeKotlinTypeProjectionOut) {
            getBuilder().append("out ");
            render(((ConeKotlinTypeProjectionOut) coneTypeProjection).getType());
        } else if (coneTypeProjection instanceof ConeKotlinType) {
            render((ConeKotlinType) coneTypeProjection);
        }
    }

    protected void render(ConeIntegerLiteralType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (type instanceof ConeIntegerLiteralConstantType) {
            getBuilder().append("ILT: " + ((ConeIntegerLiteralConstantType) type).getValue());
        } else if (type instanceof ConeIntegerConstantOperatorType) {
            getBuilder().append("IOT");
        }
    }
}
