package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SpecialFunction;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.expression.ExpressionVisitor;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.types.DynamicType;
import org.jetbrains.kotlin.types.IntersectionTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: TypeOfFIF.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J'\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u000e\"\u00020\u000f¢\u0006\u0002\u0010\u0010J\b\u0010\u0011\u001a\u00020\u000fH\u0002J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0017J\u001e\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u00072\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u001aH\u0002J\u0010\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0010\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u001fH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/KTypeConstructor;", "", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "(Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;)V", "visitedTypeParams", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lkotlin/collections/HashSet;", "callHelperFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "name", "", "arguments", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "(Ljava/lang/String;[Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "createDynamicType", "createKClassifier", "classifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", Namer.CREATE_KTYPE, ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", Namer.CREATE_KTYPE_PARAMETER, "typeParameter", "", "createKTypeProjection", "tp", "Lorg/jetbrains/kotlin/types/TypeProjection;", "createSimpleKType", "Lorg/jetbrains/kotlin/types/SimpleType;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class KTypeConstructor {
    private final TranslationContext context;
    private final HashSet<TypeParameterDescriptor> visitedTypeParams;

    /* JADX INFO: compiled from: TypeOfFIF.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Variance.values().length];
            try {
                iArr[Variance.INVARIANT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Variance.IN_VARIANCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Variance.OUT_VARIANCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public KTypeConstructor(TranslationContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.visitedTypeParams = new HashSet<>();
    }

    public final JsInvocation callHelperFunction(String name2, JsExpression... arguments) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return new JsInvocation(this.context.getReferenceToIntrinsic(name2), (JsExpression[]) Arrays.copyOf(arguments, arguments.length));
    }

    public final JsExpression createKType(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        UnwrappedType unwrappedTypeUnwrap = type.unwrap();
        if (unwrappedTypeUnwrap instanceof SimpleType) {
            return createSimpleKType((SimpleType) unwrappedTypeUnwrap);
        }
        if (unwrappedTypeUnwrap instanceof DynamicType) {
            return createDynamicType();
        }
        throw new IllegalStateException(("Unexpected type " + type).toString());
    }

    private final JsExpression createDynamicType() {
        return callHelperFunction(Namer.CREATE_DYNAMIC_KTYPE, new JsExpression[0]);
    }

    private final JsExpression createSimpleKType(SimpleType type) {
        JsInvocation jsInvocation;
        TypeConstructor constructor = type.getConstructor();
        ClassifierDescriptor this$0 = constructor.getThis$0();
        if ((this$0 instanceof TypeParameterDescriptor) && ((TypeParameterDescriptor) this$0).isReified()) {
            JsName nameForIntrinsic = this.context.getNameForIntrinsic(SpecialFunction.GET_REIFIED_TYPE_PARAMETER_KTYPE.getSuggestedName());
            Intrinsics.checkNotNullExpressionValue(nameForIntrinsic, "getNameForIntrinsic(...)");
            MetadataProperties.setSpecialFunction(nameForIntrinsic, SpecialFunction.GET_REIFIED_TYPE_PARAMETER_KTYPE);
            JsInvocation jsInvocation2 = new JsInvocation(nameForIntrinsic.makeRef(), UtilsKt.getReferenceToJsClass(this$0, this.context));
            return type.getIsMarkedNullable() ? callHelperFunction(Namer.MARK_KTYPE_NULLABLE, jsInvocation2) : jsInvocation2;
        }
        if (this$0 != null) {
            jsInvocation = createKClassifier(this$0);
        } else if (constructor instanceof IntersectionTypeConstructor) {
            JsName nameForIntrinsic2 = this.context.getNameForIntrinsic("getKClassM");
            Intrinsics.checkNotNullExpressionValue(nameForIntrinsic2, "getNameForIntrinsic(...)");
            Collection<KotlinType> collectionMo7438getSupertypes = ((IntersectionTypeConstructor) constructor).mo7438getSupertypes();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionMo7438getSupertypes, 10));
            Iterator<T> it = collectionMo7438getSupertypes.iterator();
            while (it.hasNext()) {
                arrayList.add(UtilsKt.getReferenceToJsClass(((KotlinType) it.next()).getConstructor().getThis$0(), this.context));
            }
            jsInvocation = new JsInvocation(nameForIntrinsic2.makeRef(), new JsArrayLiteral(arrayList));
        } else {
            throw new IllegalStateException(("Can't get KClass for " + type).toString());
        }
        List<TypeProjection> arguments = type.getArguments();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        Iterator<T> it2 = arguments.iterator();
        while (it2.hasNext()) {
            arrayList2.add(createKTypeProjection((TypeProjection) it2.next()));
        }
        return callHelperFunction(Namer.CREATE_KTYPE, jsInvocation, new JsArrayLiteral(arrayList2), new JsBooleanLiteral(type.getIsMarkedNullable()));
    }

    private final JsExpression createKTypeProjection(TypeProjection tp) {
        String str;
        if (tp.isStarProjection()) {
            return callHelperFunction(Namer.GET_START_KTYPE_PROJECTION, new JsExpression[0]);
        }
        int i = WhenMappings.$EnumSwitchMapping$0[tp.getProjectionKind().ordinal()];
        if (i == 1) {
            str = Namer.CREATE_INVARIANT_KTYPE_PROJECTION;
        } else if (i == 2) {
            str = Namer.CREATE_CONTRAVARIANT_KTYPE_PROJECTION;
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            str = Namer.CREATE_COVARIANT_KTYPE_PROJECTION;
        }
        KotlinType nullableAnyType = tp.getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
        return callHelperFunction(str, createKType(nullableAnyType));
    }

    private final JsExpression createKClassifier(ClassifierDescriptor classifier) {
        if (classifier instanceof TypeParameterDescriptor) {
            return createKTypeParameter((TypeParameterDescriptor) classifier, this.visitedTypeParams);
        }
        JsExpression objectKClass = ExpressionVisitor.getObjectKClass(this.context, classifier);
        Intrinsics.checkNotNullExpressionValue(objectKClass, "getObjectKClass(...)");
        return objectKClass;
    }

    private static final JsExpression createKTypeParameter$createKTypeParameterImpl(TypeParameterDescriptor typeParameterDescriptor, KTypeConstructor kTypeConstructor) {
        JsStringLiteral jsStringLiteral;
        JsStringLiteral jsStringLiteral2 = new JsStringLiteral(typeParameterDescriptor.getShortName().asString());
        List<KotlinType> upperBounds = typeParameterDescriptor.getUpperBounds();
        Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
        List<KotlinType> list = upperBounds;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (KotlinType kotlinType : list) {
            Intrinsics.checkNotNull(kotlinType);
            arrayList.add(kTypeConstructor.createKType(kotlinType));
        }
        JsArrayLiteral jsArrayLiteral = new JsArrayLiteral(arrayList);
        int i = WhenMappings.$EnumSwitchMapping$0[typeParameterDescriptor.getVariance().ordinal()];
        if (i == 1) {
            jsStringLiteral = new JsStringLiteral("invariant");
        } else if (i == 2) {
            jsStringLiteral = new JsStringLiteral("in");
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            jsStringLiteral = new JsStringLiteral("out");
        }
        if (typeParameterDescriptor.isReified()) {
            JsName nameForIntrinsic = kTypeConstructor.context.getNameForIntrinsic(SpecialFunction.GET_REIFIED_TYPE_PARAMETER_KTYPE.getSuggestedName());
            Intrinsics.checkNotNullExpressionValue(nameForIntrinsic, "getNameForIntrinsic(...)");
            MetadataProperties.setSpecialFunction(nameForIntrinsic, SpecialFunction.GET_REIFIED_TYPE_PARAMETER_KTYPE);
            return new JsInvocation(nameForIntrinsic.makeRef(), UtilsKt.getReferenceToJsClass(typeParameterDescriptor, kTypeConstructor.context));
        }
        return kTypeConstructor.callHelperFunction(Namer.CREATE_KTYPE_PARAMETER, jsStringLiteral2, jsArrayLiteral, jsStringLiteral);
    }

    private final JsExpression createKTypeParameter(TypeParameterDescriptor typeParameter, Set<TypeParameterDescriptor> visitedTypeParams) {
        if (visitedTypeParams.contains(typeParameter)) {
            JsInvocation jsInvocationCallHelperFunction = callHelperFunction(Namer.GET_START_KTYPE_PROJECTION, new JsExpression[0]);
            MetadataProperties.setKTypeWithRecursion(jsInvocationCallHelperFunction, true);
            return jsInvocationCallHelperFunction;
        }
        visitedTypeParams.add(typeParameter);
        JsExpression jsExpressionCreateKTypeParameter$createKTypeParameterImpl = createKTypeParameter$createKTypeParameterImpl(typeParameter, this);
        visitedTypeParams.remove(typeParameter);
        return jsExpressionCreateKTypeParameter$createKTypeParameterImpl;
    }
}
