package org.jetbrains.kotlin.fir.builder;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableBiMap;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.contracts.description.LogicOperationKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirExpressionRef;
import org.jetbrains.kotlin.fir.FirFunctionTarget;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.contracts.FirLegacyRawContractDescription;
import org.jetbrains.kotlin.fir.contracts.builder.FirLegacyRawContractDescriptionBuilder;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.builder.FirAnonymousObjectBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyAccessorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirReceiverParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyAccessor;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirArgumentUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirCheckedSafeCallSubject;
import org.jetbrains.kotlin.fir.expressions.FirComparisonExpression;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.expressions.FirLazyExpression;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirWrappedDelegateExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirArgumentListBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirBinaryLogicExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirCallableReferenceAccessBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirCheckedSafeCallSubjectBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirComparisonExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirConstExpressionBuilderKt;
import org.jetbrains.kotlin.fir.expressions.builder.FirElvisExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirFunctionCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirPropertyAccessExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirReturnExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirSafeCallExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirThisReceiverExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirWrappedDelegateExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.impl.FirContractCallBlock;
import org.jetbrains.kotlin.fir.expressions.impl.FirSingleExpressionBlock;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.builder.FirDelegateFieldReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirImplicitThisReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirResolvedNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirSimpleNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirDelegateFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirTypeProjectionWithVarianceBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitKMutableProperty0TypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitKMutableProperty1TypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitKMutableProperty2TypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitKProperty0TypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitKProperty1TypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitKProperty2TypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.types.ConstantValueKind;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: ConversionUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0088\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a(\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00052\b\b\u0002\u0010\u0013\u001a\u00020\u0005\u001a\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015\"\u0004\b\u0000\u0010\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0018\u001a\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c\u001a.\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010!\u001a\u0004\u0018\u00010 2\u0006\u0010\"\u001a\u00020#2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010%\u001a\u0018\u0010&\u001a\u00020\u001e2\b\u0010'\u001a\u0004\u0018\u00010 2\u0006\u0010(\u001a\u00020)\u001a\u0010\u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010,\u001a\u00020-\u001a\u0010\u0010.\u001a\u00020\u001a2\u0006\u0010/\u001a\u000200H\u0000\u001a\f\u00101\u001a\u00020\u0001*\u0004\u0018\u000102\u001a\n\u00103\u001a\u000204*\u00020 \u001a\u0016\u00105\u001a\u00020\u0001*\u00020\u000f2\b\u0010\"\u001a\u0004\u0018\u00010\u001cH\u0002\u001a\n\u00106\u001a\u000204*\u000207\u001a0\u00108\u001a\u000209*\u00020\u000f2\b\u0010:\u001a\u0004\u0018\u00010 2\b\u0010;\u001a\u0004\u0018\u00010 2\u0006\u0010<\u001a\u00020\u000f2\u0006\u0010=\u001a\u00020#H\u0002\u001a\u001a\u0010>\u001a\u00020?*\u00020\u001e2\u0006\u0010@\u001a\u00020\u000f2\u0006\u0010'\u001a\u00020 \u001a\u001e\u0010A\u001a\b\u0012\u0004\u0012\u00020B0\u0011*\b\u0012\u0004\u0012\u00020B0\u00112\u0006\u0010C\u001a\u000202\u001aR\u0010D\u001a\u00020E\"\u0004\b\u0000\u0010\u0016*\u00020F2\b\u0010G\u001a\u0004\u0018\u00010H2\u0006\u0010I\u001a\u00020J2\f\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\n2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00160\u00182\u0006\u0010K\u001a\u00020\u00012\n\b\u0002\u0010L\u001a\u0004\u0018\u00010M\u001a.\u0010N\u001a\u00020O*\u00020\u000f2\u0006\u0010<\u001a\u00020\u000f2\u0006\u0010P\u001a\u00020Q2\b\u0010;\u001a\u0004\u0018\u00010 2\b\u0010:\u001a\u0004\u0018\u00010 \u001a.\u0010R\u001a\u000209*\u00020\u000f2\u0006\u0010<\u001a\u00020\u000f2\u0006\u0010S\u001a\u00020\u00012\b\u0010;\u001a\u0004\u0018\u00010 2\b\u0010:\u001a\u0004\u0018\u00010 \u001a$\u0010T\u001a\u00020U*\u00020\u000f2\u0006\u0010V\u001a\u00020\u000f2\u0006\u0010W\u001a\u00020\u00012\b\u0010;\u001a\u0004\u0018\u00010 \u001a\u001c\u0010X\u001a\u00020Y*\u00020\u000f2\u0006\u0010V\u001a\u00020\u000f2\b\u0010;\u001a\u0004\u0018\u00010 \u001a\u001e\u0010Z\u001a\u00020E\"\u0004\b\u0000\u0010\u0016*\u00020[2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0018\u001a\u001b\u0010\\\u001a\u00020\u0001*\u00020]\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\u0010\u0000(\u0000\u001a\n\u0010^\u001a\u00020\u0001*\u00020-\u001a\n\u0010_\u001a\u00020\u001a*\u00020\u001c\u001a\n\u0010`\u001a\u00020\u000f*\u00020\u000f\u001a\f\u0010a\u001a\u0004\u0018\u00010#*\u00020Q\u001a\n\u0010b\u001a\u00020c*\u00020Q\u001a\f\u0010d\u001a\u0004\u0018\u00010c*\u00020Q\u001a\n\u0010e\u001a\u00020f*\u000209\u001a\f\u0010g\u001a\u0004\u0018\u00010#*\u00020Q\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0017\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u001f\u0010\t\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u000b\u0018\u00010\n*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\f\u0010\rò\u0001\u0004\n\u000209¨\u0006h"}, d2 = {"isUnderscore", "", "", "(Ljava/lang/CharSequence;)Z", "ownerRegularClassTypeParametersCount", "", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirClassBuilder;", "getOwnerRegularClassTypeParametersCount", "(Lorg/jetbrains/kotlin/fir/declarations/builder/FirClassBuilder;)Ljava/lang/Integer;", "ownerRegularOrAnonymousObjectSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "getOwnerRegularOrAnonymousObjectSymbol", "(Lorg/jetbrains/kotlin/fir/declarations/builder/FirClassBuilder;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "buildBalancedOrExpressionTree", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "conditions", "", "lower", "upper", "currentDispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", ExifInterface.GPS_DIRECTION_TRUE, "context", "Lorg/jetbrains/kotlin/fir/builder/Context;", "escapedStringToCharacter", "Lorg/jetbrains/kotlin/fir/builder/CharacterWithDiagnostic;", "text", "", "generateAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "qualifiedSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "calleeReferenceSource", "name", "Lorg/jetbrains/kotlin/name/Name;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "generateResolvedAccessExpression", FirebaseAnalytics.Param.SOURCE, "variable", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "processLegacyContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "translateEscape", "c", "", "appliesToPrimaryConstructorParameter", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "asReceiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "checkReceiver", "convertToReceiverParameter", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "createConventionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "operationReferenceSource", "baseSource", "argument", "conventionName", "createSafeCall", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "receiver", "filterUseSiteTarget", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "target", "generateAccessorsByDelegate", "", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirPropertyBuilder;", "delegateBuilder", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirWrappedDelegateExpressionBuilder;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "isExtension", "lazyDelegateExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirLazyExpression;", "generateComparisonExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;", "operatorToken", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "generateContainsOperation", "inverted", "generateLazyLogicalOperation", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "other", "isAnd", "generateNotNullOrOther", "Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;", "initContainingClassAttr", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "isContractBlockFirCheck", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "isContractPresentFirCheck", "parseCharacter", "pullUpSafeCallIfNecessary", "toBinaryName", "toFirOperation", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "toFirOperationOrNull", "toLegacyRawContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirLegacyRawContractDescription;", "toUnaryName", "raw-fir.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConversionUtilsKt {
    public static final FirOperation toFirOperation(IElementType iElementType) {
        Intrinsics.checkNotNullParameter(iElementType, "<this>");
        FirOperation firOperationOrNull = toFirOperationOrNull(iElementType);
        if (firOperationOrNull != null) {
            return firOperationOrNull;
        }
        throw new IllegalStateException(("Cannot convert element type to FIR operation: " + iElementType).toString());
    }

    public static final CharacterWithDiagnostic parseCharacter(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        if (str.length() < 2 || str.charAt(0) != '\'' || str.charAt(str.length() - 1) != '\'') {
            return new CharacterWithDiagnostic(DiagnosticKind.IncorrectCharacterLiteral);
        }
        String strSubstring = str.substring(1, str.length() - 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        if (strSubstring.length() == 0) {
            return new CharacterWithDiagnostic(DiagnosticKind.EmptyCharacterLiteral);
        }
        if (strSubstring.charAt(0) != '\\') {
            if (strSubstring.length() == 1) {
                return new CharacterWithDiagnostic(strSubstring.charAt(0));
            }
            return new CharacterWithDiagnostic(DiagnosticKind.TooManyCharactersInCharacterLiteral);
        }
        return escapedStringToCharacter(strSubstring);
    }

    public static final CharacterWithDiagnostic escapedStringToCharacter(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        if (text.length() > 0) {
            text.charAt(0);
        }
        String strSubstring = text.substring(1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        int length = strSubstring.length();
        if (length == 0) {
            return new CharacterWithDiagnostic(DiagnosticKind.IllegalEscape);
        }
        if (length == 1) {
            return translateEscape(strSubstring.charAt(0));
        }
        if (length == 5 && strSubstring.charAt(0) == 'u') {
            String strSubstring2 = strSubstring.substring(1);
            Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
            Integer intOrNull = StringsKt.toIntOrNull(strSubstring2, 16);
            if (intOrNull != null) {
                return new CharacterWithDiagnostic((char) intOrNull.intValue());
            }
        }
        return new CharacterWithDiagnostic(DiagnosticKind.IllegalEscape);
    }

    public static final CharacterWithDiagnostic translateEscape(char c) {
        if (c == 't') {
            return new CharacterWithDiagnostic('\t');
        }
        if (c == 'b') {
            return new CharacterWithDiagnostic('\b');
        }
        if (c == 'n') {
            return new CharacterWithDiagnostic('\n');
        }
        if (c == 'r') {
            return new CharacterWithDiagnostic('\r');
        }
        if (c == '\'') {
            return new CharacterWithDiagnostic('\'');
        }
        if (c == '\"') {
            return new CharacterWithDiagnostic('\"');
        }
        if (c == '\\') {
            return new CharacterWithDiagnostic('\\');
        }
        if (c == '$') {
            return new CharacterWithDiagnostic('$');
        }
        return new CharacterWithDiagnostic(DiagnosticKind.IllegalEscape);
    }

    public static final Name toBinaryName(IElementType iElementType) {
        Intrinsics.checkNotNullParameter(iElementType, "<this>");
        ImmutableBiMap<KtSingleValueToken, Name> BINARY_OPERATION_NAMES = OperatorConventions.BINARY_OPERATION_NAMES;
        Intrinsics.checkNotNullExpressionValue(BINARY_OPERATION_NAMES, "BINARY_OPERATION_NAMES");
        return BINARY_OPERATION_NAMES.get(iElementType);
    }

    public static final Name toUnaryName(IElementType iElementType) {
        Intrinsics.checkNotNullParameter(iElementType, "<this>");
        ImmutableBiMap<KtSingleValueToken, Name> UNARY_OPERATION_NAMES = OperatorConventions.UNARY_OPERATION_NAMES;
        Intrinsics.checkNotNullExpressionValue(UNARY_OPERATION_NAMES, "UNARY_OPERATION_NAMES");
        return UNARY_OPERATION_NAMES.get(iElementType);
    }

    public static final FirOperation toFirOperationOrNull(IElementType iElementType) {
        Intrinsics.checkNotNullParameter(iElementType, "<this>");
        if (Intrinsics.areEqual(iElementType, KtTokens.LT)) {
            return FirOperation.LT;
        }
        if (Intrinsics.areEqual(iElementType, KtTokens.GT)) {
            return FirOperation.GT;
        }
        if (Intrinsics.areEqual(iElementType, KtTokens.LTEQ)) {
            return FirOperation.LT_EQ;
        }
        if (Intrinsics.areEqual(iElementType, KtTokens.GTEQ)) {
            return FirOperation.GT_EQ;
        }
        if (Intrinsics.areEqual(iElementType, KtTokens.EQEQ)) {
            return FirOperation.EQ;
        }
        if (Intrinsics.areEqual(iElementType, KtTokens.EXCLEQ)) {
            return FirOperation.NOT_EQ;
        }
        if (Intrinsics.areEqual(iElementType, KtTokens.EQEQEQ)) {
            return FirOperation.IDENTITY;
        }
        if (Intrinsics.areEqual(iElementType, KtTokens.EXCLEQEQEQ)) {
            return FirOperation.NOT_IDENTITY;
        }
        if (Intrinsics.areEqual(iElementType, KtTokens.EQ)) {
            return FirOperation.ASSIGN;
        }
        if (Intrinsics.areEqual(iElementType, KtTokens.PLUSEQ)) {
            return FirOperation.PLUS_ASSIGN;
        }
        if (Intrinsics.areEqual(iElementType, KtTokens.MINUSEQ)) {
            return FirOperation.MINUS_ASSIGN;
        }
        if (Intrinsics.areEqual(iElementType, KtTokens.MULTEQ)) {
            return FirOperation.TIMES_ASSIGN;
        }
        if (Intrinsics.areEqual(iElementType, KtTokens.DIVEQ)) {
            return FirOperation.DIV_ASSIGN;
        }
        if (Intrinsics.areEqual(iElementType, KtTokens.PERCEQ)) {
            return FirOperation.REM_ASSIGN;
        }
        if (Intrinsics.areEqual(iElementType, KtTokens.AS_KEYWORD)) {
            return FirOperation.AS;
        }
        if (Intrinsics.areEqual(iElementType, KtTokens.AS_SAFE)) {
            return FirOperation.SAFE_AS;
        }
        return null;
    }

    public static final FirFunctionCall generateContainsOperation(FirExpression firExpression, FirExpression argument, boolean z, KtSourceElement ktSourceElement, KtSourceElement ktSourceElement2) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        Intrinsics.checkNotNullParameter(argument, "argument");
        FirFunctionCall firFunctionCallCreateConventionCall = createConventionCall(firExpression, ktSourceElement2, ktSourceElement, argument, OperatorNameConventions.CONTAINS);
        if (!z) {
            return firFunctionCallCreateConventionCall;
        }
        FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
        firFunctionCallBuilder.setSource(ktSourceElement != null ? KtSourceElementKt.fakeElement$default(ktSourceElement, KtFakeSourceElementKind.DesugaredInvertedContains.INSTANCE, 0, 0, 6, null) : null);
        FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
        firSimpleNamedReferenceBuilder.setSource(ktSourceElement2 != null ? KtSourceElementKt.fakeElement$default(ktSourceElement2, KtFakeSourceElementKind.DesugaredInvertedContains.INSTANCE, 0, 0, 6, null) : null);
        firSimpleNamedReferenceBuilder.setName(OperatorNameConventions.NOT);
        firFunctionCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
        firFunctionCallBuilder.setExplicitReceiver(firFunctionCallCreateConventionCall);
        firFunctionCallBuilder.setOrigin(FirFunctionCallOrigin.Operator);
        return firFunctionCallBuilder.mo6809build();
    }

    public static final FirComparisonExpression generateComparisonExpression(FirExpression firExpression, FirExpression argument, IElementType operatorToken, KtSourceElement ktSourceElement, KtSourceElement ktSourceElement2) {
        FirOperation firOperation;
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(operatorToken, "operatorToken");
        ImmutableSet<KtSingleValueToken> COMPARISON_OPERATIONS = OperatorConventions.COMPARISON_OPERATIONS;
        Intrinsics.checkNotNullExpressionValue(COMPARISON_OPERATIONS, "COMPARISON_OPERATIONS");
        if (!CollectionsKt.contains(COMPARISON_OPERATIONS, operatorToken)) {
            throw new IllegalArgumentException((operatorToken + " is not in " + OperatorConventions.COMPARISON_OPERATIONS).toString());
        }
        FirFunctionCall firFunctionCallCreateConventionCall = createConventionCall(firExpression, ktSourceElement2, ktSourceElement != null ? KtSourceElementKt.fakeElement$default(ktSourceElement, KtFakeSourceElementKind.GeneratedComparisonExpression.INSTANCE, 0, 0, 6, null) : null, argument, OperatorNameConventions.COMPARE_TO);
        if (Intrinsics.areEqual(operatorToken, KtTokens.LT)) {
            firOperation = FirOperation.LT;
        } else if (Intrinsics.areEqual(operatorToken, KtTokens.GT)) {
            firOperation = FirOperation.GT;
        } else if (Intrinsics.areEqual(operatorToken, KtTokens.LTEQ)) {
            firOperation = FirOperation.LT_EQ;
        } else {
            if (!Intrinsics.areEqual(operatorToken, KtTokens.GTEQ)) {
                throw new IllegalStateException(("Unknown " + operatorToken).toString());
            }
            firOperation = FirOperation.GT_EQ;
        }
        FirComparisonExpressionBuilder firComparisonExpressionBuilder = new FirComparisonExpressionBuilder();
        firComparisonExpressionBuilder.setSource(ktSourceElement);
        firComparisonExpressionBuilder.setOperation(firOperation);
        firComparisonExpressionBuilder.setCompareToCall(firFunctionCallCreateConventionCall);
        return firComparisonExpressionBuilder.mo6809build();
    }

    public static /* synthetic */ FirQualifiedAccessExpression generateAccessExpression$default(KtSourceElement ktSourceElement, KtSourceElement ktSourceElement2, Name name2, ConeDiagnostic coneDiagnostic, int i, Object obj) {
        if ((i & 8) != 0) {
            coneDiagnostic = null;
        }
        return generateAccessExpression(ktSourceElement, ktSourceElement2, name2, coneDiagnostic);
    }

    public static final FirClassSymbol<? extends FirClass> getOwnerRegularOrAnonymousObjectSymbol(FirClassBuilder firClassBuilder) {
        Intrinsics.checkNotNullParameter(firClassBuilder, "<this>");
        if (firClassBuilder instanceof FirAnonymousObjectBuilder) {
            return ((FirAnonymousObjectBuilder) firClassBuilder).getSymbol();
        }
        if (firClassBuilder instanceof FirRegularClassBuilder) {
            return ((FirRegularClassBuilder) firClassBuilder).getSymbol();
        }
        return null;
    }

    public static final Integer getOwnerRegularClassTypeParametersCount(FirClassBuilder firClassBuilder) {
        Intrinsics.checkNotNullParameter(firClassBuilder, "<this>");
        if (firClassBuilder instanceof FirRegularClassBuilder) {
            return Integer.valueOf(firClassBuilder.getTypeParameters().size());
        }
        return null;
    }

    public static /* synthetic */ void generateAccessorsByDelegate$default(FirPropertyBuilder firPropertyBuilder, FirWrappedDelegateExpressionBuilder firWrappedDelegateExpressionBuilder, FirModuleData firModuleData, FirClassSymbol firClassSymbol, Context context, boolean z, FirLazyExpression firLazyExpression, int i, Object obj) {
        if ((i & 32) != 0) {
            firLazyExpression = null;
        }
        generateAccessorsByDelegate(firPropertyBuilder, firWrappedDelegateExpressionBuilder, firModuleData, firClassSymbol, context, z, firLazyExpression);
    }

    public static final <T> void generateAccessorsByDelegate(FirPropertyBuilder firPropertyBuilder, FirWrappedDelegateExpressionBuilder firWrappedDelegateExpressionBuilder, FirModuleData moduleData, FirClassSymbol<?> firClassSymbol, Context<T> context, boolean z, FirLazyExpression firLazyExpression) {
        boolean z2;
        KtSourceElement ktSourceElement;
        FirWrappedDelegateExpression firWrappedDelegateExpressionBuild;
        KtSourceElement ktSourceElementFakeElement$default;
        Visibilities.Unknown visibility;
        List<FirAnnotation> list;
        KtSourceElement source;
        KtSourceElement ktSourceElementFakeElement$default2;
        Visibilities.Unknown visibility2;
        KtSourceElement source2;
        List<FirValueParameter> valueParameters;
        FirValueParameter firValueParameter;
        Intrinsics.checkNotNullParameter(firPropertyBuilder, "<this>");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(context, "context");
        if (firWrappedDelegateExpressionBuilder == null) {
            return;
        }
        FirDelegateFieldSymbol firDelegateFieldSymbol = new FirDelegateFieldSymbol(firPropertyBuilder.getSymbol().getCallableId());
        firPropertyBuilder.setDelegateFieldSymbol(firDelegateFieldSymbol);
        boolean z3 = firClassSymbol != null;
        KtSourceElement source3 = firWrappedDelegateExpressionBuilder.getSource();
        KtSourceElement ktSourceElementFakeElement$default3 = source3 != null ? KtSourceElementKt.fakeElement$default(source3, KtFakeSourceElementKind.DelegatedPropertyAccessor.INSTANCE, 0, 0, 6, null) : null;
        boolean zIsVar = firPropertyBuilder.isVar();
        if (firLazyExpression != null) {
            firWrappedDelegateExpressionBuild = firLazyExpression;
            z2 = zIsVar;
            ktSourceElement = ktSourceElementFakeElement$default3;
        } else {
            FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
            firFunctionCallBuilder.setExplicitReceiver(firWrappedDelegateExpressionBuilder.getExpression());
            FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
            firSimpleNamedReferenceBuilder.setSource(ktSourceElementFakeElement$default3);
            firSimpleNamedReferenceBuilder.setName(OperatorNameConventions.PROVIDE_DELEGATE);
            firFunctionCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
            z2 = zIsVar;
            ktSourceElement = ktSourceElementFakeElement$default3;
            firFunctionCallBuilder.setArgumentList(FirArgumentUtilKt.buildBinaryArgumentList(generateAccessorsByDelegate$thisRef(z, firClassSymbol, ktSourceElementFakeElement$default3, firPropertyBuilder, context, true), generateAccessorsByDelegate$propertyRef(ktSourceElement, z3, z, z2, firPropertyBuilder)));
            firFunctionCallBuilder.setOrigin(FirFunctionCallOrigin.Operator);
            firFunctionCallBuilder.setSource(ktSourceElement);
            firWrappedDelegateExpressionBuilder.setDelegateProvider(firFunctionCallBuilder.mo6809build());
            firWrappedDelegateExpressionBuild = firWrappedDelegateExpressionBuilder.mo6809build();
        }
        firPropertyBuilder.setDelegate(firWrappedDelegateExpressionBuild);
        if (firPropertyBuilder.getGetter() == null || (firPropertyBuilder.getGetter() instanceof FirDefaultPropertyAccessor)) {
            FirPropertyAccessor getter = firPropertyBuilder.getGetter();
            List<FirAnnotation> annotations = getter != null ? getter.getAnnotations() : null;
            FirFunctionTarget firFunctionTarget = new FirFunctionTarget(null, false);
            FirPropertyAccessor getter2 = firPropertyBuilder.getGetter();
            FirDeclarationStatus status = getter2 != null ? getter2.getStatus() : null;
            FirPropertyAccessor getter3 = firPropertyBuilder.getGetter();
            if (getter3 == null || (source = getter3.getSource()) == null || (ktSourceElementFakeElement$default = KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.DelegatedPropertyAccessor.INSTANCE, 0, 0, 6, null)) == null) {
                ktSourceElementFakeElement$default = ktSourceElement;
            }
            FirPropertyAccessorBuilder firPropertyAccessorBuilder = new FirPropertyAccessorBuilder();
            firPropertyAccessorBuilder.setSource(ktSourceElementFakeElement$default);
            firPropertyAccessorBuilder.setModuleData(moduleData);
            firPropertyAccessorBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            firPropertyAccessorBuilder.setReturnTypeRef(FirImplicitTypeRefImplWithoutSource.INSTANCE);
            firPropertyAccessorBuilder.setGetter(true);
            if (status == null || (visibility = status.getVisibility()) == null) {
                visibility = Visibilities.Unknown.INSTANCE;
            }
            FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(visibility, Modality.FINAL);
            firDeclarationStatusImpl.setInline(status != null ? status.isInline() : firDeclarationStatusImpl.isInline());
            firPropertyAccessorBuilder.setStatus(firDeclarationStatusImpl);
            firPropertyAccessorBuilder.setSymbol(new FirPropertyAccessorSymbol());
            FirReturnExpressionBuilder firReturnExpressionBuilder = new FirReturnExpressionBuilder();
            FirFunctionCallBuilder firFunctionCallBuilder2 = new FirFunctionCallBuilder();
            firFunctionCallBuilder2.setSource(ktSourceElement);
            firFunctionCallBuilder2.setExplicitReceiver(generateAccessorsByDelegate$delegateAccess(ktSourceElement, firClassSymbol, firDelegateFieldSymbol, z, firPropertyBuilder, context));
            FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder2 = new FirSimpleNamedReferenceBuilder();
            firSimpleNamedReferenceBuilder2.setSource(ktSourceElement);
            firSimpleNamedReferenceBuilder2.setName(OperatorNameConventions.GET_VALUE);
            firFunctionCallBuilder2.setCalleeReference(firSimpleNamedReferenceBuilder2.build());
            list = null;
            firFunctionCallBuilder2.setArgumentList(FirArgumentUtilKt.buildBinaryArgumentList(generateAccessorsByDelegate$thisRef$default(z, firClassSymbol, ktSourceElement, firPropertyBuilder, context, false, 32, null), generateAccessorsByDelegate$propertyRef(ktSourceElement, z3, z, z2, firPropertyBuilder)));
            firFunctionCallBuilder2.setOrigin(FirFunctionCallOrigin.Operator);
            firReturnExpressionBuilder.setResult(firFunctionCallBuilder2.mo6809build());
            firReturnExpressionBuilder.setTarget(firFunctionTarget);
            firReturnExpressionBuilder.setSource(ktSourceElement);
            firPropertyAccessorBuilder.setBody(new FirSingleExpressionBlock(firReturnExpressionBuilder.mo6809build()));
            if (annotations != null) {
                firPropertyAccessorBuilder.getAnnotations().addAll(annotations);
            }
            firPropertyAccessorBuilder.setPropertySymbol(firPropertyBuilder.getSymbol());
            FirPropertyAccessor firPropertyAccessorBuild = firPropertyAccessorBuilder.mo6809build();
            firFunctionTarget.bind(firPropertyAccessorBuild);
            initContainingClassAttr(firPropertyAccessorBuild, context);
            firPropertyBuilder.setGetter(firPropertyAccessorBuild);
        } else {
            list = null;
        }
        if (z2) {
            if (firPropertyBuilder.getSetter() == null || (firPropertyBuilder.getSetter() instanceof FirDefaultPropertyAccessor)) {
                FirPropertyAccessor setter = firPropertyBuilder.getSetter();
                List<FirAnnotation> annotations2 = setter != null ? setter.getAnnotations() : list;
                FirPropertyAccessor setter2 = firPropertyBuilder.getSetter();
                List<FirAnnotation> annotations3 = (setter2 == null || (valueParameters = setter2.getValueParameters()) == null || (firValueParameter = (FirValueParameter) CollectionsKt.firstOrNull((List) valueParameters)) == null) ? list : firValueParameter.getAnnotations();
                FirPropertyAccessor setter3 = firPropertyBuilder.getSetter();
                FirDeclarationStatus status2 = setter3 != null ? setter3.getStatus() : list;
                FirPropertyAccessor setter4 = firPropertyBuilder.getSetter();
                if (setter4 == null || (source2 = setter4.getSource()) == null || (ktSourceElementFakeElement$default2 = KtSourceElementKt.fakeElement$default(source2, KtFakeSourceElementKind.DelegatedPropertyAccessor.INSTANCE, 0, 0, 6, null)) == null) {
                    ktSourceElementFakeElement$default2 = ktSourceElement;
                }
                FirPropertyAccessorBuilder firPropertyAccessorBuilder2 = new FirPropertyAccessorBuilder();
                firPropertyAccessorBuilder2.setSource(ktSourceElementFakeElement$default2);
                firPropertyAccessorBuilder2.setModuleData(moduleData);
                firPropertyAccessorBuilder2.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                firPropertyAccessorBuilder2.setReturnTypeRef(moduleData.getSession().getBuiltinTypes().getUnitType());
                firPropertyAccessorBuilder2.setGetter(false);
                if (status2 == null || (visibility2 = status2.getVisibility()) == null) {
                    visibility2 = Visibilities.Unknown.INSTANCE;
                }
                FirDeclarationStatusImpl firDeclarationStatusImpl2 = new FirDeclarationStatusImpl(visibility2, Modality.FINAL);
                firDeclarationStatusImpl2.setInline(status2 != null ? status2.isInline() : firDeclarationStatusImpl2.isInline());
                firPropertyAccessorBuilder2.setStatus(firDeclarationStatusImpl2);
                firPropertyAccessorBuilder2.setSymbol(new FirPropertyAccessorSymbol());
                FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
                firValueParameterBuilder.setSource(ktSourceElement);
                firValueParameterBuilder.setContainingFunctionSymbol(firPropertyAccessorBuilder2.getSymbol());
                firValueParameterBuilder.setModuleData(moduleData);
                firValueParameterBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                firValueParameterBuilder.setReturnTypeRef(FirImplicitTypeRefImplWithoutSource.INSTANCE);
                firValueParameterBuilder.setName(SpecialNames.IMPLICIT_SET_PARAMETER);
                firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(firPropertyBuilder.getName()));
                firValueParameterBuilder.setCrossinline(false);
                firValueParameterBuilder.setNoinline(false);
                firValueParameterBuilder.setVararg(false);
                if (annotations3 != null) {
                    firValueParameterBuilder.getAnnotations().addAll(annotations3);
                }
                FirValueParameter firValueParameterBuild = firValueParameterBuilder.mo6809build();
                firPropertyAccessorBuilder2.getValueParameters().add(firValueParameterBuild);
                FirFunctionCallBuilder firFunctionCallBuilder3 = new FirFunctionCallBuilder();
                firFunctionCallBuilder3.setSource(ktSourceElement);
                firFunctionCallBuilder3.setExplicitReceiver(generateAccessorsByDelegate$delegateAccess(ktSourceElement, firClassSymbol, firDelegateFieldSymbol, z, firPropertyBuilder, context));
                FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder3 = new FirSimpleNamedReferenceBuilder();
                firSimpleNamedReferenceBuilder3.setSource(ktSourceElement);
                firSimpleNamedReferenceBuilder3.setName(OperatorNameConventions.SET_VALUE);
                firFunctionCallBuilder3.setCalleeReference(firSimpleNamedReferenceBuilder3.build());
                FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
                firArgumentListBuilder.getArguments().add(generateAccessorsByDelegate$thisRef$default(z, firClassSymbol, ktSourceElement, firPropertyBuilder, context, false, 32, null));
                firArgumentListBuilder.getArguments().add(generateAccessorsByDelegate$propertyRef(ktSourceElement, z3, z, z2, firPropertyBuilder));
                List<FirExpression> arguments = firArgumentListBuilder.getArguments();
                FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder = new FirPropertyAccessExpressionBuilder();
                FirResolvedNamedReferenceBuilder firResolvedNamedReferenceBuilder = new FirResolvedNamedReferenceBuilder();
                firResolvedNamedReferenceBuilder.setSource(ktSourceElement);
                firResolvedNamedReferenceBuilder.setName(SpecialNames.IMPLICIT_SET_PARAMETER);
                firResolvedNamedReferenceBuilder.setResolvedSymbol(firValueParameterBuild.getSymbol());
                firPropertyAccessExpressionBuilder.setCalleeReference(firResolvedNamedReferenceBuilder.build());
                arguments.add(firPropertyAccessExpressionBuilder.mo6809build());
                firFunctionCallBuilder3.setArgumentList(firArgumentListBuilder.build());
                firFunctionCallBuilder3.setOrigin(FirFunctionCallOrigin.Operator);
                firPropertyAccessorBuilder2.setBody(new FirSingleExpressionBlock(firFunctionCallBuilder3.mo6809build()));
                if (annotations2 != null) {
                    firPropertyAccessorBuilder2.getAnnotations().addAll(annotations2);
                }
                firPropertyAccessorBuilder2.setPropertySymbol(firPropertyBuilder.getSymbol());
                FirPropertyAccessor firPropertyAccessorBuild2 = firPropertyAccessorBuilder2.mo6809build();
                initContainingClassAttr(firPropertyAccessorBuild2, context);
                firPropertyBuilder.setSetter(firPropertyAccessorBuild2);
            }
        }
    }

    static /* synthetic */ FirExpression generateAccessorsByDelegate$thisRef$default(boolean z, FirClassSymbol firClassSymbol, KtSourceElement ktSourceElement, FirPropertyBuilder firPropertyBuilder, Context context, boolean z2, int i, Object obj) {
        if ((i & 32) != 0) {
            z2 = false;
        }
        return generateAccessorsByDelegate$thisRef(z, firClassSymbol, ktSourceElement, firPropertyBuilder, context, z2);
    }

    public static final FirContractDescription processLegacyContractDescription(FirBlock block) {
        Intrinsics.checkNotNullParameter(block, "block");
        if (isContractPresentFirCheck(block)) {
            return toLegacyRawContractDescription((FirFunctionCall) FirExpressionUtilKt.replaceFirstStatement(block, new Function1<FirFunctionCall, FirStatement>() { // from class: org.jetbrains.kotlin.fir.builder.ConversionUtilsKt$processLegacyContractDescription$contractCall$1
                @Override // kotlin.jvm.functions.Function1
                public final FirStatement invoke(FirFunctionCall it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return new FirContractCallBlock(it);
                }
            }));
        }
        return null;
    }

    public static final boolean isContractPresentFirCheck(FirBlock firBlock) {
        Intrinsics.checkNotNullParameter(firBlock, "<this>");
        FirStatement firStatement = (FirStatement) CollectionsKt.firstOrNull((List) firBlock.getStatements());
        if (firStatement == null) {
            return false;
        }
        return isContractBlockFirCheck(firStatement);
    }

    public static final boolean isContractBlockFirCheck(FirStatement firStatement) {
        Intrinsics.checkNotNullParameter(firStatement, "<this>");
        FirFunctionCall firFunctionCall = firStatement instanceof FirFunctionCall ? (FirFunctionCall) firStatement : null;
        if (firFunctionCall == null || !Intrinsics.areEqual(firFunctionCall.getCalleeReference().getName().asString(), "contract")) {
            return false;
        }
        FirExpression explicitReceiver = firFunctionCall.getExplicitReceiver();
        FirQualifiedAccessExpression firQualifiedAccessExpression = explicitReceiver instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) explicitReceiver : null;
        if (firQualifiedAccessExpression == null) {
            return true;
        }
        if (!checkReceiver(firFunctionCall, "contracts") || !checkReceiver(firQualifiedAccessExpression, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME)) {
            return false;
        }
        FirExpression explicitReceiver2 = firQualifiedAccessExpression.getExplicitReceiver();
        FirQualifiedAccessExpression firQualifiedAccessExpression2 = explicitReceiver2 instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) explicitReceiver2 : null;
        return firQualifiedAccessExpression2 != null && firQualifiedAccessExpression2.getExplicitReceiver() == null;
    }

    private static final boolean checkReceiver(FirExpression firExpression, String str) {
        Name name2;
        String strAsString;
        if (!(firExpression instanceof FirQualifiedAccessExpression)) {
            return false;
        }
        FirExpression explicitReceiver = ((FirQualifiedAccessExpression) firExpression).getExplicitReceiver();
        FirQualifiedAccessExpression firQualifiedAccessExpression = explicitReceiver instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) explicitReceiver : null;
        if (firQualifiedAccessExpression == null) {
            return false;
        }
        FirReference calleeReference = firQualifiedAccessExpression.getCalleeReference();
        FirNamedReference firNamedReference = calleeReference instanceof FirNamedReference ? (FirNamedReference) calleeReference : null;
        if (firNamedReference == null || (name2 = firNamedReference.getName()) == null || (strAsString = name2.asString()) == null) {
            return false;
        }
        return Intrinsics.areEqual(strAsString, str);
    }

    public static final FirExpression pullUpSafeCallIfNecessary(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        if (!(firExpression instanceof FirQualifiedAccessExpression)) {
            return firExpression;
        }
        FirQualifiedAccessExpression firQualifiedAccessExpression = (FirQualifiedAccessExpression) firExpression;
        FirExpression explicitReceiver = firQualifiedAccessExpression.getExplicitReceiver();
        FirSafeCallExpression firSafeCallExpression = explicitReceiver instanceof FirSafeCallExpression ? (FirSafeCallExpression) explicitReceiver : null;
        if (firSafeCallExpression == null) {
            return firExpression;
        }
        FirStatement selector = firSafeCallExpression.getSelector();
        FirExpression firExpression2 = selector instanceof FirExpression ? (FirExpression) selector : null;
        if (firExpression2 == null) {
            return firExpression;
        }
        firQualifiedAccessExpression.replaceExplicitReceiver(firExpression2);
        firSafeCallExpression.replaceSelector(firExpression);
        return firSafeCallExpression;
    }

    public static final List<FirAnnotationCall> filterUseSiteTarget(List<? extends FirAnnotationCall> list, AnnotationUseSiteTarget target) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        ArrayList arrayList = new ArrayList();
        for (FirAnnotationCall firAnnotationCall : list) {
            FirAnnotationCall firAnnotationCallBuild = null;
            if (firAnnotationCall.getUseSiteTarget() == target) {
                FirAnnotationCallBuilder firAnnotationCallBuilder = new FirAnnotationCallBuilder();
                firAnnotationCallBuilder.setSource(firAnnotationCall.getSource());
                firAnnotationCallBuilder.setUseSiteTarget(firAnnotationCall.getUseSiteTarget());
                firAnnotationCallBuilder.setAnnotationTypeRef(firAnnotationCall.getAnnotationTypeRef());
                firAnnotationCallBuilder.getTypeArguments().addAll(firAnnotationCall.getTypeArguments());
                firAnnotationCallBuilder.setArgumentList(firAnnotationCall.getArgumentList());
                firAnnotationCallBuilder.setCalleeReference(firAnnotationCall.getCalleeReference());
                firAnnotationCallBuilder.setArgumentMapping(firAnnotationCall.getArgumentMapping());
                firAnnotationCallBuilder.setAnnotationResolvePhase(firAnnotationCall.getAnnotationResolvePhase());
                KtSourceElement source = firAnnotationCall.getSource();
                firAnnotationCallBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.FromUseSiteTarget.INSTANCE, 0, 0, 6, null) : null);
                firAnnotationCallBuild = firAnnotationCallBuilder.mo6809build();
            }
            if (firAnnotationCallBuild != null) {
                arrayList.add(firAnnotationCallBuild);
            }
        }
        return arrayList;
    }

    public static final <T> void initContainingClassAttr(FirCallableDeclaration firCallableDeclaration, Context<T> context) {
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        ConeClassLikeType coneClassLikeTypeCurrentDispatchReceiverType = currentDispatchReceiverType(context);
        if (coneClassLikeTypeCurrentDispatchReceiverType == null || (lookupTag = coneClassLikeTypeCurrentDispatchReceiverType.getLookupTag()) == null) {
            return;
        }
        ClassMembersKt.setContainingClassForStaticMemberAttr(firCallableDeclaration, lookupTag);
    }

    public static final <T> ConeClassLikeType currentDispatchReceiverType(Context<T> context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return (ConeClassLikeType) CollectionsKt.lastOrNull((List) context.getDispatchReceiverTypesStack());
    }

    public static /* synthetic */ FirExpression buildBalancedOrExpressionTree$default(List list, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = CollectionsKt.getLastIndex(list);
        }
        return buildBalancedOrExpressionTree(list, i, i2);
    }

    public static final FirExpression buildBalancedOrExpressionTree(List<? extends FirExpression> conditions, int i, int i2) {
        Intrinsics.checkNotNullParameter(conditions, "conditions");
        int i3 = (((i2 - i) + 1) / 2) + i;
        if (i == i2) {
            return conditions.get(i3);
        }
        FirExpression firExpressionBuildBalancedOrExpressionTree = buildBalancedOrExpressionTree(conditions, i, i3 - 1);
        FirExpression firExpressionBuildBalancedOrExpressionTree2 = buildBalancedOrExpressionTree(conditions, i3, i2);
        KtSourceElement source = firExpressionBuildBalancedOrExpressionTree.getSource();
        if (source == null) {
            source = firExpressionBuildBalancedOrExpressionTree2.getSource();
        }
        KtSourceElement ktSourceElement = source;
        return generateLazyLogicalOperation(firExpressionBuildBalancedOrExpressionTree, firExpressionBuildBalancedOrExpressionTree2, false, ktSourceElement != null ? KtSourceElementKt.fakeElement$default(ktSourceElement, KtFakeSourceElementKind.WhenCondition.INSTANCE, 0, 0, 6, null) : null);
    }

    public static final boolean appliesToPrimaryConstructorParameter(AnnotationUseSiteTarget annotationUseSiteTarget) {
        return annotationUseSiteTarget == null || annotationUseSiteTarget == AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER || annotationUseSiteTarget == AnnotationUseSiteTarget.RECEIVER || annotationUseSiteTarget == AnnotationUseSiteTarget.FILE;
    }

    public static final FirElvisExpression generateNotNullOrOther(FirExpression firExpression, FirExpression other, KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        FirElvisExpressionBuilder firElvisExpressionBuilder = new FirElvisExpressionBuilder();
        firElvisExpressionBuilder.setSource(ktSourceElement);
        firElvisExpressionBuilder.setLhs(firExpression);
        firElvisExpressionBuilder.setRhs(other);
        return firElvisExpressionBuilder.mo6809build();
    }

    public static final FirBinaryLogicExpression generateLazyLogicalOperation(FirExpression firExpression, FirExpression other, boolean z, KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        FirBinaryLogicExpressionBuilder firBinaryLogicExpressionBuilder = new FirBinaryLogicExpressionBuilder();
        firBinaryLogicExpressionBuilder.setSource(ktSourceElement);
        firBinaryLogicExpressionBuilder.setLeftOperand(firExpression);
        firBinaryLogicExpressionBuilder.setRightOperand(other);
        firBinaryLogicExpressionBuilder.setKind(z ? LogicOperationKind.AND : LogicOperationKind.OR);
        return firBinaryLogicExpressionBuilder.mo6809build();
    }

    private static final FirFunctionCall createConventionCall(FirExpression firExpression, KtSourceElement ktSourceElement, KtSourceElement ktSourceElement2, FirExpression firExpression2, Name name2) {
        FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
        firFunctionCallBuilder.setSource(ktSourceElement2);
        FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
        firSimpleNamedReferenceBuilder.setSource(ktSourceElement);
        firSimpleNamedReferenceBuilder.setName(name2);
        firFunctionCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
        firFunctionCallBuilder.setExplicitReceiver(firExpression);
        firFunctionCallBuilder.setArgumentList(FirArgumentUtilKt.buildUnaryArgumentList(firExpression2));
        firFunctionCallBuilder.setOrigin(FirFunctionCallOrigin.Operator);
        return firFunctionCallBuilder.mo6809build();
    }

    public static final FirQualifiedAccessExpression generateAccessExpression(KtSourceElement ktSourceElement, KtSourceElement ktSourceElement2, Name name2, ConeDiagnostic coneDiagnostic) {
        Intrinsics.checkNotNullParameter(name2, "name");
        FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder = new FirPropertyAccessExpressionBuilder();
        firPropertyAccessExpressionBuilder.setSource(ktSourceElement);
        FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
        if (Intrinsics.areEqual(ktSourceElement2, ktSourceElement)) {
            ktSourceElement2 = ktSourceElement2 != null ? KtSourceElementKt.fakeElement$default(ktSourceElement2, KtFakeSourceElementKind.ReferenceInAtomicQualifiedAccess.INSTANCE, 0, 0, 6, null) : null;
        }
        firSimpleNamedReferenceBuilder.setSource(ktSourceElement2);
        firSimpleNamedReferenceBuilder.setName(name2);
        firPropertyAccessExpressionBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
        if (coneDiagnostic != null) {
            firPropertyAccessExpressionBuilder.getNonFatalDiagnostics().add(coneDiagnostic);
        }
        return firPropertyAccessExpressionBuilder.mo6809build();
    }

    public static final FirQualifiedAccessExpression generateResolvedAccessExpression(KtSourceElement ktSourceElement, FirVariable variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder = new FirPropertyAccessExpressionBuilder();
        firPropertyAccessExpressionBuilder.setSource(ktSourceElement);
        FirResolvedNamedReferenceBuilder firResolvedNamedReferenceBuilder = new FirResolvedNamedReferenceBuilder();
        firResolvedNamedReferenceBuilder.setSource(ktSourceElement);
        firResolvedNamedReferenceBuilder.setName(variable.getName());
        firResolvedNamedReferenceBuilder.setResolvedSymbol(variable.getSymbol());
        firPropertyAccessExpressionBuilder.setCalleeReference(firResolvedNamedReferenceBuilder.build());
        return firPropertyAccessExpressionBuilder.mo6809build();
    }

    public static final FirLegacyRawContractDescription toLegacyRawContractDescription(FirFunctionCall firFunctionCall) {
        Intrinsics.checkNotNullParameter(firFunctionCall, "<this>");
        FirLegacyRawContractDescriptionBuilder firLegacyRawContractDescriptionBuilder = new FirLegacyRawContractDescriptionBuilder();
        firLegacyRawContractDescriptionBuilder.setSource(firFunctionCall.getSource());
        firLegacyRawContractDescriptionBuilder.setContractCall(firFunctionCall);
        return firLegacyRawContractDescriptionBuilder.build();
    }

    public static final FirSafeCallExpression createSafeCall(FirQualifiedAccessExpression firQualifiedAccessExpression, FirExpression receiver, KtSourceElement source) {
        Intrinsics.checkNotNullParameter(firQualifiedAccessExpression, "<this>");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.checkNotNullParameter(source, "source");
        FirCheckedSafeCallSubjectBuilder firCheckedSafeCallSubjectBuilder = new FirCheckedSafeCallSubjectBuilder();
        FirExpressionRef<FirExpression> firExpressionRef = new FirExpressionRef<>();
        firExpressionRef.bind(receiver);
        firCheckedSafeCallSubjectBuilder.setOriginalReceiverRef(firExpressionRef);
        KtSourceElement source2 = receiver.getSource();
        firCheckedSafeCallSubjectBuilder.setSource(source2 != null ? KtSourceElementKt.fakeElement$default(source2, KtFakeSourceElementKind.CheckedSafeCallSubject.INSTANCE, 0, 0, 6, null) : null);
        FirCheckedSafeCallSubject firCheckedSafeCallSubjectBuild = firCheckedSafeCallSubjectBuilder.mo6809build();
        firQualifiedAccessExpression.replaceExplicitReceiver(firCheckedSafeCallSubjectBuild);
        FirSafeCallExpressionBuilder firSafeCallExpressionBuilder = new FirSafeCallExpressionBuilder();
        firSafeCallExpressionBuilder.setReceiver(receiver);
        FirExpressionRef<FirCheckedSafeCallSubject> firExpressionRef2 = new FirExpressionRef<>();
        firExpressionRef2.bind(firCheckedSafeCallSubjectBuild);
        firSafeCallExpressionBuilder.setCheckedSubjectRef(firExpressionRef2);
        firSafeCallExpressionBuilder.setSelector(firQualifiedAccessExpression);
        firSafeCallExpressionBuilder.setSource(source);
        return firSafeCallExpressionBuilder.mo6809build();
    }

    public static final FirReceiverParameter convertToReceiverParameter(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        FirReceiverParameterBuilder firReceiverParameterBuilder = new FirReceiverParameterBuilder();
        KtSourceElement source = firTypeRef.getSource();
        firReceiverParameterBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ReceiverFromType.INSTANCE, 0, 0, 6, null) : null);
        List<FirAnnotation> annotations = firReceiverParameterBuilder.getAnnotations();
        List<FirAnnotation> annotations2 = firTypeRef.getAnnotations();
        Intrinsics.checkNotNull(annotations2, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.fir.expressions.FirAnnotationCall>");
        CollectionsKt.addAll(annotations, filterUseSiteTarget(annotations2, AnnotationUseSiteTarget.RECEIVER));
        List<FirAnnotation> annotations3 = firTypeRef.getAnnotations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : annotations3) {
            if (((FirAnnotation) obj).getUseSiteTarget() != AnnotationUseSiteTarget.RECEIVER) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.size() != firTypeRef.getAnnotations().size()) {
            firTypeRef.replaceAnnotations(arrayList2);
        }
        firReceiverParameterBuilder.setTypeRef(firTypeRef);
        return firReceiverParameterBuilder.mo6809build();
    }

    public static final FirReceiverParameter asReceiverParameter(KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(ktSourceElement, "<this>");
        FirReceiverParameterBuilder firReceiverParameterBuilder = new FirReceiverParameterBuilder();
        firReceiverParameterBuilder.setSource(KtSourceElementKt.fakeElement$default(ktSourceElement, KtFakeSourceElementKind.ReceiverFromType.INSTANCE, 0, 0, 6, null));
        firReceiverParameterBuilder.setTypeRef(FirImplicitTypeRefImplWithoutSource.INSTANCE);
        return firReceiverParameterBuilder.mo6809build();
    }

    public static final boolean isUnderscore(CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        for (int i = 0; i < charSequence.length(); i++) {
            if (charSequence.charAt(i) != '_') {
                return false;
            }
        }
        return true;
    }

    private static final <T> FirExpression generateAccessorsByDelegate$thisRef(boolean z, FirClassSymbol<?> firClassSymbol, KtSourceElement ktSourceElement, FirPropertyBuilder firPropertyBuilder, Context<T> context, boolean z2) {
        if (z && !z2) {
            FirThisReceiverExpressionBuilder firThisReceiverExpressionBuilder = new FirThisReceiverExpressionBuilder();
            firThisReceiverExpressionBuilder.setSource(ktSourceElement);
            FirImplicitThisReferenceBuilder firImplicitThisReferenceBuilder = new FirImplicitThisReferenceBuilder();
            firImplicitThisReferenceBuilder.setBoundSymbol(firPropertyBuilder.getSymbol());
            firThisReceiverExpressionBuilder.setCalleeReference(firImplicitThisReferenceBuilder.build());
            return firThisReceiverExpressionBuilder.mo6809build();
        }
        if (firClassSymbol == null) {
            return FirConstExpressionBuilderKt.buildConstExpression$default(null, ConstantValueKind.Null.INSTANCE, null, null, false, 8, null);
        }
        FirThisReceiverExpressionBuilder firThisReceiverExpressionBuilder2 = new FirThisReceiverExpressionBuilder();
        firThisReceiverExpressionBuilder2.setSource(ktSourceElement);
        FirImplicitThisReferenceBuilder firImplicitThisReferenceBuilder2 = new FirImplicitThisReferenceBuilder();
        firImplicitThisReferenceBuilder2.setBoundSymbol(firClassSymbol);
        firThisReceiverExpressionBuilder2.setCalleeReference(firImplicitThisReferenceBuilder2.build());
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setType((ConeKotlinType) CollectionsKt.last((List) context.getDispatchReceiverTypesStack()));
        firThisReceiverExpressionBuilder2.setTypeRef(firResolvedTypeRefBuilder.mo6809build());
        return firThisReceiverExpressionBuilder2.mo6809build();
    }

    private static final <T> FirPropertyAccessExpression generateAccessorsByDelegate$delegateAccess(KtSourceElement ktSourceElement, FirClassSymbol<?> firClassSymbol, FirDelegateFieldSymbol firDelegateFieldSymbol, boolean z, FirPropertyBuilder firPropertyBuilder, Context<T> context) {
        FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder = new FirPropertyAccessExpressionBuilder();
        firPropertyAccessExpressionBuilder.setSource(ktSourceElement);
        FirDelegateFieldReferenceBuilder firDelegateFieldReferenceBuilder = new FirDelegateFieldReferenceBuilder();
        firDelegateFieldReferenceBuilder.setResolvedSymbol(firDelegateFieldSymbol);
        firPropertyAccessExpressionBuilder.setCalleeReference(firDelegateFieldReferenceBuilder.build());
        if (firClassSymbol != null) {
            firPropertyAccessExpressionBuilder.setDispatchReceiver(generateAccessorsByDelegate$thisRef(z, firClassSymbol, ktSourceElement, firPropertyBuilder, context, true));
        }
        return firPropertyAccessExpressionBuilder.mo6809build();
    }

    private static final FirCallableReferenceAccess generateAccessorsByDelegate$propertyRef(KtSourceElement ktSourceElement, boolean z, boolean z2, boolean z3, FirPropertyBuilder firPropertyBuilder) {
        FirImplicitKProperty1TypeRef firImplicitKProperty1TypeRef;
        FirTypeRef firTypeRef;
        FirImplicitKProperty2TypeRef firImplicitKProperty2TypeRef;
        FirImplicitKProperty0TypeRef firImplicitKProperty0TypeRef;
        FirCallableReferenceAccessBuilder firCallableReferenceAccessBuilder = new FirCallableReferenceAccessBuilder();
        firCallableReferenceAccessBuilder.setSource(ktSourceElement);
        FirResolvedNamedReferenceBuilder firResolvedNamedReferenceBuilder = new FirResolvedNamedReferenceBuilder();
        firResolvedNamedReferenceBuilder.setSource(ktSourceElement);
        firResolvedNamedReferenceBuilder.setName(firPropertyBuilder.getName());
        firResolvedNamedReferenceBuilder.setResolvedSymbol(firPropertyBuilder.getSymbol());
        firCallableReferenceAccessBuilder.setCalleeReference(firResolvedNamedReferenceBuilder.build());
        if (!z && !z2) {
            if (z3) {
                firImplicitKProperty0TypeRef = new FirImplicitKMutableProperty0TypeRef(null, ConeStarProjection.INSTANCE);
            } else {
                firImplicitKProperty0TypeRef = new FirImplicitKProperty0TypeRef(null, ConeStarProjection.INSTANCE);
            }
            firTypeRef = firImplicitKProperty0TypeRef;
        } else if (z && z2) {
            if (z3) {
                firImplicitKProperty2TypeRef = new FirImplicitKMutableProperty2TypeRef(null, ConeStarProjection.INSTANCE, ConeStarProjection.INSTANCE, ConeStarProjection.INSTANCE);
            } else {
                firImplicitKProperty2TypeRef = new FirImplicitKProperty2TypeRef(null, ConeStarProjection.INSTANCE, ConeStarProjection.INSTANCE, ConeStarProjection.INSTANCE);
            }
            firTypeRef = firImplicitKProperty2TypeRef;
        } else {
            if (z3) {
                firImplicitKProperty1TypeRef = new FirImplicitKMutableProperty1TypeRef(null, ConeStarProjection.INSTANCE, ConeStarProjection.INSTANCE);
            } else {
                firImplicitKProperty1TypeRef = new FirImplicitKProperty1TypeRef(null, ConeStarProjection.INSTANCE, ConeStarProjection.INSTANCE);
            }
            firTypeRef = firImplicitKProperty1TypeRef;
        }
        firCallableReferenceAccessBuilder.setTypeRef(firTypeRef);
        List<FirTypeParameter> typeParameters = firPropertyBuilder.getTypeParameters();
        List<FirTypeProjection> typeArguments = firCallableReferenceAccessBuilder.getTypeArguments();
        for (FirTypeParameter firTypeParameter : typeParameters) {
            FirTypeProjectionWithVarianceBuilder firTypeProjectionWithVarianceBuilder = new FirTypeProjectionWithVarianceBuilder();
            firTypeProjectionWithVarianceBuilder.setSource(ktSourceElement);
            firTypeProjectionWithVarianceBuilder.setVariance(Variance.INVARIANT);
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setType(new ConeTypeParameterTypeImpl(firTypeParameter.getSymbol().getLookupTag(), false, null, 4, null));
            firTypeProjectionWithVarianceBuilder.setTypeRef(firResolvedTypeRefBuilder.mo6809build());
            typeArguments.add(firTypeProjectionWithVarianceBuilder.build());
        }
        return firCallableReferenceAccessBuilder.mo6809build();
    }
}
