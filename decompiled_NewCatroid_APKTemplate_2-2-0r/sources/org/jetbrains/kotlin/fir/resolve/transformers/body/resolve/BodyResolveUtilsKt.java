package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.CopyUtilsKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.builder.FirVarargArgumentsExpressionBuilder;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralTypeImplKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.ConstantValueKind;

/* JADX INFO: compiled from: BodyResolveUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001aX\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\n`\u000b2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\"\u0010\u000f\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\n`\u000bH\u0000\u001a\u0016\u0010\u0010\u001a\u00020\u000e*\u0006\u0012\u0002\b\u00030\u00112\u0006\u0010\u0012\u001a\u00020\u0013\u001a\u0012\u0010\u0014\u001a\u00020\u0015*\u00020\u00162\u0006\u0010\u0012\u001a\u00020\u0013\"*\u0010\u0002\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\u0000\u001a\u00020\u00018À\u0002@À\u0002X\u0080\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\u0017"}, d2 = {ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "resultType", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getResultType", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setResultType", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "remapArgumentsWithVararg", "Ljava/util/LinkedHashMap;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lkotlin/collections/LinkedHashMap;", "varargParameter", "varargArrayType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "argumentMapping", "expectedConeType", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "writeResultType", "", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BodyResolveUtilsKt {
    public static final FirTypeRef getResultType(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        return firExpression.getTypeRef();
    }

    public static final void setResultType(FirExpression firExpression, FirTypeRef type) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        firExpression.replaceTypeRef(type);
    }

    public static final LinkedHashMap<FirExpression, FirValueParameter> remapArgumentsWithVararg(FirValueParameter varargParameter, ConeKotlinType varargArrayType, LinkedHashMap<FirExpression, FirValueParameter> argumentMapping) {
        Intrinsics.checkNotNullParameter(varargParameter, "varargParameter");
        Intrinsics.checkNotNullParameter(varargArrayType, "varargArrayType");
        Intrinsics.checkNotNullParameter(argumentMapping, "argumentMapping");
        FirTypeRef returnTypeRef = varargParameter.getReturnTypeRef();
        ConeKotlinType coneKotlinTypeArrayElementType = ArrayUtilsKt.arrayElementType(varargArrayType);
        ConeKotlinType coneKotlinTypeApproximateIntegerLiteralType$default = coneKotlinTypeArrayElementType != null ? ConeIntegerLiteralTypeImplKt.approximateIntegerLiteralType$default(coneKotlinTypeArrayElementType, null, 1, null) : null;
        Set<FirExpression> setKeySet = argumentMapping.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
        List<FirExpression> list = CollectionsKt.toList(setKeySet);
        int size = list.size();
        LinkedHashMap<FirExpression, FirValueParameter> linkedHashMap = new LinkedHashMap<>();
        FirVarargArgumentsExpressionBuilder firVarargArgumentsExpressionBuilder = new FirVarargArgumentsExpressionBuilder();
        firVarargArgumentsExpressionBuilder.setVarargElementType(TypeUtilsKt.withReplacedConeType(returnTypeRef, coneKotlinTypeApproximateIntegerLiteralType$default, KtFakeSourceElementKind.VarargArgument.INSTANCE));
        firVarargArgumentsExpressionBuilder.setTypeRef(TypeUtilsKt.withReplacedConeType(returnTypeRef, varargArrayType, KtFakeSourceElementKind.VarargArgument.INSTANCE));
        KtSourceElement source = null;
        int i = 0;
        int iMin = Integer.MAX_VALUE;
        int iMax = 0;
        for (FirExpression firExpression : list) {
            int i2 = i + 1;
            Intrinsics.checkNotNull(firExpression);
            FirValueParameter firValueParameter = (FirValueParameter) MapsKt.getValue(argumentMapping, firExpression);
            if (Intrinsics.areEqual(firValueParameter, varargParameter) || (firValueParameter.getIsVararg() && !(firExpression instanceof FirNamedArgumentExpression))) {
                firVarargArgumentsExpressionBuilder.getArguments().add(firExpression);
                KtSourceElement source2 = firExpression.getSource();
                iMin = Math.min(iMin, source2 != null ? source2.getStartOffset() : Integer.MAX_VALUE);
                KtSourceElement source3 = firExpression.getSource();
                iMax = Math.max(iMax, source3 != null ? source3.getEndOffset() : 0);
                if (source == null) {
                    source = firExpression.getSource();
                }
            } else {
                if (!firVarargArgumentsExpressionBuilder.getArguments().isEmpty()) {
                    size = i;
                    break;
                }
                linkedHashMap.put(firExpression, firValueParameter);
            }
            i = i2;
        }
        firVarargArgumentsExpressionBuilder.setSource(source != null ? KtSourceElementKt.fakeElement(source, KtFakeSourceElementKind.VarargArgument.INSTANCE, iMin, iMax) : null);
        LinkedHashMap<FirExpression, FirValueParameter> linkedHashMap2 = linkedHashMap;
        linkedHashMap2.put(firVarargArgumentsExpressionBuilder.mo6809build(), varargParameter);
        int size2 = list.size();
        while (size < size2) {
            Object obj = list.get(size);
            Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
            FirExpression firExpression2 = (FirExpression) obj;
            linkedHashMap2.put(firExpression2, MapsKt.getValue(argumentMapping, firExpression2));
            size++;
        }
        return linkedHashMap;
    }

    public static final void writeResultType(FirBlock firBlock, FirSession session) {
        FirErrorTypeRef firErrorTypeRefMo6809build;
        FirTypeRef firTypeRefResolvedTypeFromPrototype$default;
        Intrinsics.checkNotNullParameter(firBlock, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirStatement firStatement = (FirStatement) CollectionsKt.lastOrNull((List) firBlock.getStatements());
        FirExpression firExpression = firStatement instanceof FirExpression ? (FirExpression) firStatement : null;
        FirBlock firBlock2 = firBlock;
        if (firExpression == null) {
            firTypeRefResolvedTypeFromPrototype$default = CopyUtilsKt.resolvedTypeFromPrototype$default(firBlock2.getTypeRef(), session.getBuiltinTypes().getUnitType().getType(), null, 2, null);
        } else {
            FirTypeRef typeRef = firExpression.getTypeRef();
            if (typeRef instanceof FirResolvedTypeRef) {
                firErrorTypeRefMo6809build = (FirResolvedTypeRef) UtilsKt.copyWithNewSourceKind(typeRef, KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE);
            } else {
                FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                firErrorTypeRefBuilder.setDiagnostic(new ConeSimpleDiagnostic("No type for block", DiagnosticKind.InferenceError));
                firErrorTypeRefMo6809build = firErrorTypeRefBuilder.mo6809build();
            }
            firTypeRefResolvedTypeFromPrototype$default = firErrorTypeRefMo6809build;
        }
        firBlock2.replaceTypeRef(firTypeRefResolvedTypeFromPrototype$default);
    }

    static /* synthetic */ ConeKotlinType expectedConeType$constructLiteralType$default(FirSession firSession, ClassId classId, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return expectedConeType$constructLiteralType(firSession, classId, z);
    }

    private static final ConeKotlinType expectedConeType$constructLiteralType(FirSession firSession, ClassId classId, boolean z) {
        FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(firSession).getClassLikeSymbolByClassId(classId);
        if (classLikeSymbolByClassId == null) {
            return new ConeErrorType(new ConeSimpleDiagnostic("Missing stdlib class: " + classId, DiagnosticKind.MissingStdlibClass), false, null, null, 14, null);
        }
        return TypeConstructionUtilsKt.constructClassType$default(classLikeSymbolByClassId.getLookupTag(), new ConeTypeProjection[0], z, null, 4, null);
    }

    public static final ConeKotlinType expectedConeType(ConstantValueKind<?> constantValueKind, FirSession session) {
        Intrinsics.checkNotNullParameter(constantValueKind, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Null.INSTANCE)) {
            return session.getBuiltinTypes().getNullableNothingType().getType();
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Boolean.INSTANCE)) {
            return session.getBuiltinTypes().getBooleanType().getType();
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Char.INSTANCE)) {
            return expectedConeType$constructLiteralType$default(session, StandardClassIds.INSTANCE.getChar(), false, 4, null);
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Byte.INSTANCE)) {
            return expectedConeType$constructLiteralType$default(session, StandardClassIds.INSTANCE.getByte(), false, 4, null);
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Short.INSTANCE)) {
            return expectedConeType$constructLiteralType$default(session, StandardClassIds.INSTANCE.getShort(), false, 4, null);
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Int.INSTANCE)) {
            return expectedConeType$constructLiteralType$default(session, StandardClassIds.INSTANCE.getInt(), false, 4, null);
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Long.INSTANCE)) {
            return expectedConeType$constructLiteralType$default(session, StandardClassIds.INSTANCE.getLong(), false, 4, null);
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.String.INSTANCE)) {
            return expectedConeType$constructLiteralType$default(session, StandardClassIds.INSTANCE.getString(), false, 4, null);
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Float.INSTANCE)) {
            return expectedConeType$constructLiteralType$default(session, StandardClassIds.INSTANCE.getFloat(), false, 4, null);
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Double.INSTANCE)) {
            return expectedConeType$constructLiteralType$default(session, StandardClassIds.INSTANCE.getDouble(), false, 4, null);
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.UnsignedByte.INSTANCE)) {
            return expectedConeType$constructLiteralType$default(session, StandardClassIds.INSTANCE.getUByte(), false, 4, null);
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.UnsignedShort.INSTANCE)) {
            return expectedConeType$constructLiteralType$default(session, StandardClassIds.INSTANCE.getUShort(), false, 4, null);
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.UnsignedInt.INSTANCE)) {
            return expectedConeType$constructLiteralType$default(session, StandardClassIds.INSTANCE.getUInt(), false, 4, null);
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.UnsignedLong.INSTANCE)) {
            return expectedConeType$constructLiteralType$default(session, StandardClassIds.INSTANCE.getULong(), false, 4, null);
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.IntegerLiteral.INSTANCE)) {
            return expectedConeType$constructLiteralType$default(session, StandardClassIds.INSTANCE.getInt(), false, 4, null);
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.UnsignedIntegerLiteral.INSTANCE)) {
            return expectedConeType$constructLiteralType$default(session, StandardClassIds.INSTANCE.getUInt(), false, 4, null);
        }
        if (Intrinsics.areEqual(constantValueKind, ConstantValueKind.Error.INSTANCE)) {
            throw new IllegalStateException("Unexpected error ConstantValueKind".toString());
        }
        throw new NoWhenBranchMatchedException();
    }
}
