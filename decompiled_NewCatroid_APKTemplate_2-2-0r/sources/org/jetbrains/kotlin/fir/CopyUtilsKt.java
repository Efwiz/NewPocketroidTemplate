package org.jetbrains.kotlin.fir;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirImplicitInvokeCall;
import org.jetbrains.kotlin.fir.expressions.builder.FirImplicitInvokeCallBuilder;
import org.jetbrains.kotlin.fir.extensions.FirExtensionServiceKt;
import org.jetbrains.kotlin.fir.extensions.FirTypeAttributeExtension;
import org.jetbrains.kotlin.fir.extensions.FirTypeAttributeExtensionKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeAttribute;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.CustomAnnotationTypeAttribute;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: CopyUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000h\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a@\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u00052\u0012\b\u0002\u0010\u0006\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u00022\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b\u001a)\u0010\f\u001a\u00020\r*\u00020\u000e2\u0017\u0010\u000f\u001a\u0013\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010¢\u0006\u0002\b\u0013H\u0086\bø\u0001\u0000\u001a\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0015*\u00020\u0003H\u0002¢\u0006\u0002\u0010\u0016\u001a\u001e\u0010\u0017\u001a\u00020\u0018*\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u001d\u001a\u0016\u0010\u001e\u001a\u0004\u0018\u00010\u001f*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006 "}, d2 = {"computeTypeAttributes", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "predefined", "Lorg/jetbrains/kotlin/fir/types/ConeAttribute;", "containerDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "shouldExpandTypeAliases", "", "copyAsImplicitInvokeCall", "Lorg/jetbrains/kotlin/fir/expressions/FirImplicitInvokeCall;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "setupCopy", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirImplicitInvokeCallBuilder;", "", "Lkotlin/ExtensionFunctionType;", "extractContextReceiversCount", "", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;)Ljava/lang/Integer;", "resolvedTypeFromPrototype", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "fallbackSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "tryExpandClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CopyUtilsKt {
    public static /* synthetic */ FirResolvedTypeRef resolvedTypeFromPrototype$default(FirTypeRef firTypeRef, ConeKotlinType coneKotlinType, KtSourceElement ktSourceElement, int i, Object obj) {
        if ((i & 2) != 0) {
            ktSourceElement = null;
        }
        return resolvedTypeFromPrototype(firTypeRef, coneKotlinType, ktSourceElement);
    }

    public static final FirResolvedTypeRef resolvedTypeFromPrototype(FirTypeRef firTypeRef, ConeKotlinType type, KtSourceElement ktSourceElement) {
        FirTypeRef delegatedTypeRef;
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        if (type instanceof ConeErrorType) {
            FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
            KtSourceElement source = firTypeRef.getSource();
            if (source != null) {
                ktSourceElement = source;
            }
            firErrorTypeRefBuilder.setSource(ktSourceElement);
            firErrorTypeRefBuilder.setType(type);
            firErrorTypeRefBuilder.setDiagnostic(((ConeErrorType) type).getDiagnostic());
            return firErrorTypeRefBuilder.mo6809build();
        }
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        KtSourceElement source2 = firTypeRef.getSource();
        if (source2 != null) {
            ktSourceElement = source2;
        }
        firResolvedTypeRefBuilder.setSource(ktSourceElement);
        firResolvedTypeRefBuilder.setType(type);
        if (firTypeRef instanceof FirResolvedTypeRef) {
            delegatedTypeRef = ((FirResolvedTypeRef) firTypeRef).getDelegatedTypeRef();
        } else {
            delegatedTypeRef = firTypeRef instanceof FirUserTypeRef ? firTypeRef : null;
        }
        firResolvedTypeRefBuilder.setDelegatedTypeRef(delegatedTypeRef);
        CollectionsKt.addAll(firResolvedTypeRefBuilder.getAnnotations(), firTypeRef.getAnnotations());
        return firResolvedTypeRefBuilder.mo6809build();
    }

    public static /* synthetic */ ConeAttributes computeTypeAttributes$default(List list, FirSession firSession, List list2, FirDeclaration firDeclaration, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            list2 = CollectionsKt.emptyList();
        }
        if ((i & 4) != 0) {
            firDeclaration = null;
        }
        return computeTypeAttributes(list, firSession, list2, firDeclaration, z);
    }

    public static final ConeAttributes computeTypeAttributes(List<? extends FirAnnotation> list, FirSession session, List<? extends ConeAttribute<?>> predefined, FirDeclaration firDeclaration, boolean z) {
        ClassId classId;
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(predefined, "predefined");
        if (list.isEmpty()) {
            return predefined.isEmpty() ? ConeAttributes.INSTANCE.getEmpty() : ConeAttributes.INSTANCE.create(predefined);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = arrayList;
        CollectionsKt.addAll(arrayList2, predefined);
        ArrayList arrayList3 = new ArrayList();
        Iterator<? extends FirAnnotation> it = list.iterator();
        while (true) {
            ConeAttribute<?> coneAttribute = null;
            if (it.hasNext()) {
                FirAnnotation next = it.next();
                if (z) {
                    classId = tryExpandClassId(next, session);
                } else if (!z) {
                    classId = ConeTypeUtilsKt.getClassId(FirTypeUtilsKt.getConeType(next.getTypeRef()));
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                if (Intrinsics.areEqual(classId, CompilerConeAttributes.Exact.INSTANCE.getANNOTATION_CLASS_ID())) {
                    arrayList2.add(CompilerConeAttributes.Exact.INSTANCE);
                } else if (Intrinsics.areEqual(classId, CompilerConeAttributes.NoInfer.INSTANCE.getANNOTATION_CLASS_ID())) {
                    arrayList2.add(CompilerConeAttributes.NoInfer.INSTANCE);
                } else if (Intrinsics.areEqual(classId, CompilerConeAttributes.ExtensionFunctionType.INSTANCE.getANNOTATION_CLASS_ID())) {
                    arrayList2.add(CompilerConeAttributes.ExtensionFunctionType.INSTANCE);
                } else if (Intrinsics.areEqual(classId, CompilerConeAttributes.ContextFunctionTypeParams.INSTANCE.getANNOTATION_CLASS_ID())) {
                    Integer numExtractContextReceiversCount = extractContextReceiversCount(next);
                    arrayList2.add(new CompilerConeAttributes.ContextFunctionTypeParams(numExtractContextReceiversCount != null ? numExtractContextReceiversCount.intValue() : 0));
                } else if (Intrinsics.areEqual(classId, CompilerConeAttributes.UnsafeVariance.INSTANCE.getANNOTATION_CLASS_ID())) {
                    arrayList2.add(CompilerConeAttributes.UnsafeVariance.INSTANCE);
                } else {
                    Iterator<T> it2 = FirTypeAttributeExtensionKt.getTypeAttributeExtensions(FirExtensionServiceKt.getExtensionService(session)).iterator();
                    while (it2.hasNext()) {
                        ConeAttribute<?> coneAttributeExtractAttributeFromAnnotation = ((FirTypeAttributeExtension) it2.next()).extractAttributeFromAnnotation(next);
                        if (coneAttributeExtractAttributeFromAnnotation != null) {
                            coneAttribute = coneAttributeExtractAttributeFromAnnotation;
                            break;
                        }
                    }
                    if (coneAttribute != null) {
                        arrayList2.add(coneAttribute);
                    } else {
                        arrayList3.add(next);
                    }
                }
            } else {
                if (!arrayList3.isEmpty()) {
                    arrayList2.add(new CustomAnnotationTypeAttribute(arrayList3, firDeclaration != null ? firDeclaration.getSymbol() : null));
                }
                return ConeAttributes.INSTANCE.create(arrayList);
            }
        }
    }

    private static final Integer extractContextReceiversCount(FirAnnotation firAnnotation) {
        FirExpression firExpression = firAnnotation.getArgumentMapping().getMapping().get(StandardNames.CONTEXT_FUNCTION_TYPE_PARAMETER_COUNT_NAME);
        FirConstExpression firConstExpression = firExpression instanceof FirConstExpression ? (FirConstExpression) firExpression : null;
        Object value = firConstExpression != null ? firConstExpression.getValue() : null;
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return null;
    }

    public static final FirImplicitInvokeCall copyAsImplicitInvokeCall(FirFunctionCall firFunctionCall, Function1<? super FirImplicitInvokeCallBuilder, Unit> setupCopy) {
        Intrinsics.checkNotNullParameter(firFunctionCall, "<this>");
        Intrinsics.checkNotNullParameter(setupCopy, "setupCopy");
        FirImplicitInvokeCallBuilder firImplicitInvokeCallBuilder = new FirImplicitInvokeCallBuilder();
        firImplicitInvokeCallBuilder.setSource(firFunctionCall.getSource());
        firImplicitInvokeCallBuilder.getAnnotations().addAll(firFunctionCall.getAnnotations());
        firImplicitInvokeCallBuilder.getTypeArguments().addAll(firFunctionCall.getTypeArguments());
        firImplicitInvokeCallBuilder.setExplicitReceiver(firFunctionCall.getExplicitReceiver());
        firImplicitInvokeCallBuilder.setDispatchReceiver(firFunctionCall.getDispatchReceiver());
        firImplicitInvokeCallBuilder.setExtensionReceiver(firFunctionCall.getExtensionReceiver());
        firImplicitInvokeCallBuilder.setArgumentList(firFunctionCall.getArgumentList());
        firImplicitInvokeCallBuilder.setCalleeReference(firFunctionCall.getCalleeReference());
        setupCopy.invoke(firImplicitInvokeCallBuilder);
        return firImplicitInvokeCallBuilder.mo6809build();
    }

    private static final ClassId tryExpandClassId(FirAnnotation firAnnotation, FirSession firSession) {
        FirTypeRef annotationTypeRef = firAnnotation.getAnnotationTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        ConeClassLikeType coneClassLikeType = type instanceof ConeClassLikeType ? (ConeClassLikeType) type : null;
        ConeClassLikeType coneClassLikeTypeDirectExpansionType = coneClassLikeType != null ? TypeExpansionUtilsKt.directExpansionType(coneClassLikeType, firSession, new Function1<FirTypeAlias, ConeClassLikeType>() { // from class: org.jetbrains.kotlin.fir.CopyUtilsKt$tryExpandClassId$directlyExpanded$1
            @Override // kotlin.jvm.functions.Function1
            public final ConeClassLikeType invoke(FirTypeAlias it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return FirDeclarationUtilKt.getExpandedConeType(it);
            }
        }) : null;
        if (coneClassLikeTypeDirectExpansionType != null) {
            return ConeTypeUtilsKt.getClassId(TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeTypeDirectExpansionType, firSession, null, 2, null));
        }
        FirTypeRef annotationTypeRef2 = firAnnotation.getAnnotationTypeRef();
        FirResolvedTypeRef firResolvedTypeRef2 = annotationTypeRef2 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef2 : null;
        ConeKotlinType type2 = firResolvedTypeRef2 != null ? firResolvedTypeRef2.getType() : null;
        ConeClassLikeType coneClassLikeType2 = type2 instanceof ConeClassLikeType ? (ConeClassLikeType) type2 : null;
        if (coneClassLikeType2 != null) {
            return ConeTypeUtilsKt.getClassId(coneClassLikeType2);
        }
        return null;
    }
}
