package org.jetbrains.kotlin.backend.common.linkage.partial;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.com.intellij.util.PathUtil;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetSingletonValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.linkage.partial.ExploredClassifier;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageCase;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnTargetSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IdSignatureRendererKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: PartialLinkageErrorMessages.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¶\u0001\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a \u0010\r\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0011H\u0002\u001a$\u0010\u0012\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\u0006\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u0015H\u0002\u001a\u001c\u0010\u0016\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002\u001a$\u0010\u0003\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0015H\u0002\u001a$\u0010\u001a\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0015H\u0002\u001a\u001c\u0010\u001b\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\u0006\u0010\u0019\u001a\u00020\u0005H\u0002\u001a\u001c\u0010\u001c\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\u0006\u0010\u0019\u001a\u00020\u0005H\u0002\u001a,\u0010\u001d\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\u0006\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0015H\u0002\u001a4\u0010\b\u001a\u00060\u000ej\u0002`\u000f*\u00060\"j\u0002`#2\u0006\u0010\b\u001a\u00020\n2\u0016\u0010$\u001a\u0012\u0012\u0004\u0012\u00020&\u0012\b\u0012\u00060\u000ej\u0002`\u000f0%H\u0002\u001a$\u0010'\u001a\u00060\u000ej\u0002`\u000f*\u00060\"j\u0002`#2\u0006\u0010\b\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020 H\u0002\u001a\u000e\u0010(\u001a\u0004\u0018\u00010\u0001*\u00020\u0005H\u0002\u001a*\u0010)\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\u0006\u0010\b\u001a\u00020*2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020-0,H\u0002\u001a,\u0010.\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\u0006\u0010/\u001a\u00020\u00052\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u000201H\u0002\u001a,\u00103\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\u00182\u0006\u00107\u001a\u000205H\u0002\u001a4\u00108\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\u0006\u0010\b\u001a\u0002092\f\u0010:\u001a\b\u0012\u0004\u0012\u00020;0,2\b\u0010<\u001a\u0004\u0018\u00010=H\u0002\u001a<\u0010>\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020\u00152\u0006\u0010B\u001a\u00020\u00152\u0006\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020DH\u0002\u001a\u001c\u0010F\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\u0006\u0010F\u001a\u000201H\u0002\u001a\u001c\u0010G\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\u0006\u0010\u0019\u001a\u00020\u0005H\u0002\u001a\f\u0010H\u001a\u00020\u0001*\u00020IH\u0000\u001a\u001c\u0010J\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\u0006\u0010\u0019\u001a\u00020\u0005H\u0002\u001a\"\u0010K\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\f\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00050,H\u0002\u001a\"\u0010M\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\f\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00050,H\u0002\u001a\u0014\u0010N\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000fH\u0002\u001a \u0010O\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0011H\u0002\u001a$\u0010P\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\u0006\u0010Q\u001a\u0002052\u0006\u0010R\u001a\u00020\u0005H\u0002\u001a,\u0010S\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010T\u001a\u00020U2\u0006\u0010V\u001a\u00020\u0015H\u0002\u001a$\u0010W\u001a\u00060\u000ej\u0002`\u000f*\u00060\u000ej\u0002`\u000f2\u0006\u0010X\u001a\u00020\u00052\u0006\u0010Y\u001a\u00020\u0001H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\"\u0018\u0010\b\u001a\u00020\t*\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006Z"}, d2 = {"UNKNOWN_FILE", "", "UNKNOWN_SYMBOL", "declarationKind", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/DeclarationKind;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getDeclarationKind", "(Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)Lorg/jetbrains/kotlin/backend/common/linkage/partial/DeclarationKind;", "expression", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/Expression;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getExpression", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)Lorg/jetbrains/kotlin/backend/common/linkage/partial/Expression;", "ambiguousNonOverriddenCallable", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "callable", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "appendCapitalized", "text", "capitalized", "", "cantInstantiateAbstractClass", "classSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", NativeSymbol.TYPE_NAME, "declarationKindName", "declarationName", "declarationNameIsKind", "declarationWithUnusableClassifier", "declarationSymbol", "cause", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", "forExpression", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "continuation", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/ExpressionKind;", "expressionWithUnusableClassifier", "guessName", "illegalNonLocalReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "validReturnTargets", "", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "inaccessibleDeclaration", "referencedDeclarationSymbol", "declaringModule", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module;", "useSiteModule", "invalidConstructorDelegation", "constructorSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "superClassSymbol", "unexpectedSuperClassConstructorSymbol", "invalidSamConversion", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "abstractFunctionSymbols", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "abstractPropertySymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "memberAccessExpressionArgumentsMismatch", "functionSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "expressionHasDispatchReceiver", "functionHasDispatchReceiver", "expressionValueArgumentCount", "", "functionValueParameterCount", "module", "noDeclarationForSymbol", "renderLinkageError", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "signature", "sortedDeclarationsKindName", "symbols", "sortedDeclarationsName", "suspendableCallWithoutCoroutine", "unimplementedAbstractCallable", "unusableAnnotation", "annotationConstructorSymbol", "holderDeclarationSymbol", "unusableClassifier", "rendering", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/CauseRendering;", "printIntermediateCause", "wrongTypeOfDeclaration", "actualDeclarationSymbol", "expectedDeclarationDescription", "ir.serialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PartialLinkageErrorMessagesKt {

    /* JADX INFO: compiled from: PartialLinkageErrorMessages.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassKind.INTERFACE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ClassKind.ENUM_CLASS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ClassKind.ENUM_ENTRY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ClassKind.ANNOTATION_CLASS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[ClassKind.OBJECT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[DeclarationKind.values().length];
            try {
                iArr2[DeclarationKind.ANONYMOUS_OBJECT.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[DeclarationKind.LAMBDA.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public static final String renderLinkageError(final PartialLinkageCase partialLinkageCase) throws IOException {
        Intrinsics.checkNotNullParameter(partialLinkageCase, "<this>");
        final StringBuilder sb = new StringBuilder();
        if (partialLinkageCase instanceof PartialLinkageCase.UnusableClassifier) {
            unusableClassifier(sb, ((PartialLinkageCase.UnusableClassifier) partialLinkageCase).getCause(), CauseRendering.Standalone.INSTANCE, false);
        } else if (partialLinkageCase instanceof PartialLinkageCase.MissingDeclaration) {
            noDeclarationForSymbol(sb, ((PartialLinkageCase.MissingDeclaration) partialLinkageCase).getMissingDeclarationSymbol());
        } else if (partialLinkageCase instanceof PartialLinkageCase.DeclarationWithUnusableClassifier) {
            PartialLinkageCase.DeclarationWithUnusableClassifier declarationWithUnusableClassifier = (PartialLinkageCase.DeclarationWithUnusableClassifier) partialLinkageCase;
            declarationWithUnusableClassifier(sb, declarationWithUnusableClassifier.getDeclarationSymbol(), declarationWithUnusableClassifier.getCause(), false);
        } else if (partialLinkageCase instanceof PartialLinkageCase.ExpressionWithUnusableClassifier) {
            PartialLinkageCase.ExpressionWithUnusableClassifier expressionWithUnusableClassifier = (PartialLinkageCase.ExpressionWithUnusableClassifier) partialLinkageCase;
            expressionWithUnusableClassifier(sb, expressionWithUnusableClassifier.getExpression(), expressionWithUnusableClassifier.getCause());
        } else if (partialLinkageCase instanceof PartialLinkageCase.ExpressionWithMissingDeclaration) {
            expression(sb, ((PartialLinkageCase.ExpressionWithMissingDeclaration) partialLinkageCase).getExpression(), new Function1<ExpressionKind, Appendable>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageErrorMessagesKt$renderLinkageError$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Appendable invoke(ExpressionKind it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return PartialLinkageErrorMessagesKt.noDeclarationForSymbol(sb, ((PartialLinkageCase.ExpressionWithMissingDeclaration) partialLinkageCase).getMissingDeclarationSymbol());
                }
            });
        } else if (partialLinkageCase instanceof PartialLinkageCase.ExpressionHasDeclarationWithUnusableClassifier) {
            expression(sb, ((PartialLinkageCase.ExpressionHasDeclarationWithUnusableClassifier) partialLinkageCase).getExpression(), new Function1<ExpressionKind, Appendable>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageErrorMessagesKt$renderLinkageError$1$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Appendable invoke(ExpressionKind it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return PartialLinkageErrorMessagesKt.declarationWithUnusableClassifier(sb, ((PartialLinkageCase.ExpressionHasDeclarationWithUnusableClassifier) partialLinkageCase).getReferencedDeclarationSymbol(), ((PartialLinkageCase.ExpressionHasDeclarationWithUnusableClassifier) partialLinkageCase).getCause(), true);
                }
            });
        } else if (partialLinkageCase instanceof PartialLinkageCase.ExpressionHasWrongTypeOfDeclaration) {
            expression(sb, ((PartialLinkageCase.ExpressionHasWrongTypeOfDeclaration) partialLinkageCase).getExpression(), new Function1<ExpressionKind, Appendable>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageErrorMessagesKt$renderLinkageError$1$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Appendable invoke(ExpressionKind it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return PartialLinkageErrorMessagesKt.wrongTypeOfDeclaration(sb, ((PartialLinkageCase.ExpressionHasWrongTypeOfDeclaration) partialLinkageCase).getActualDeclarationSymbol(), ((PartialLinkageCase.ExpressionHasWrongTypeOfDeclaration) partialLinkageCase).getExpectedDeclarationDescription());
                }
            });
        } else if (partialLinkageCase instanceof PartialLinkageCase.MemberAccessExpressionArgumentsMismatch) {
            expression(sb, ((PartialLinkageCase.MemberAccessExpressionArgumentsMismatch) partialLinkageCase).getExpression(), new Function1<ExpressionKind, Appendable>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageErrorMessagesKt$renderLinkageError$1$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Appendable invoke(ExpressionKind it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return PartialLinkageErrorMessagesKt.memberAccessExpressionArgumentsMismatch(sb, (IrFunctionSymbol) ((PartialLinkageCase.MemberAccessExpressionArgumentsMismatch) partialLinkageCase).getExpression().getSymbol(), ((PartialLinkageCase.MemberAccessExpressionArgumentsMismatch) partialLinkageCase).getExpressionHasDispatchReceiver(), ((PartialLinkageCase.MemberAccessExpressionArgumentsMismatch) partialLinkageCase).getFunctionHasDispatchReceiver(), ((PartialLinkageCase.MemberAccessExpressionArgumentsMismatch) partialLinkageCase).getExpressionValueArgumentCount(), ((PartialLinkageCase.MemberAccessExpressionArgumentsMismatch) partialLinkageCase).getFunctionValueParameterCount());
                }
            });
        } else if (partialLinkageCase instanceof PartialLinkageCase.InvalidSamConversion) {
            expression(sb, ((PartialLinkageCase.InvalidSamConversion) partialLinkageCase).getExpression(), new Function1<ExpressionKind, Appendable>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageErrorMessagesKt$renderLinkageError$1$5
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Appendable invoke(ExpressionKind it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return PartialLinkageErrorMessagesKt.invalidSamConversion(sb, ((PartialLinkageCase.InvalidSamConversion) partialLinkageCase).getExpression(), ((PartialLinkageCase.InvalidSamConversion) partialLinkageCase).getAbstractFunctionSymbols(), ((PartialLinkageCase.InvalidSamConversion) partialLinkageCase).getAbstractPropertySymbol());
                }
            });
        } else if (partialLinkageCase instanceof PartialLinkageCase.SuspendableFunctionCallWithoutCoroutineContext) {
            expression(sb, ((PartialLinkageCase.SuspendableFunctionCallWithoutCoroutineContext) partialLinkageCase).getExpression(), new Function1<ExpressionKind, Appendable>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageErrorMessagesKt$renderLinkageError$1$6
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Appendable invoke(ExpressionKind it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return PartialLinkageErrorMessagesKt.suspendableCallWithoutCoroutine(sb);
                }
            });
        } else if (partialLinkageCase instanceof PartialLinkageCase.IllegalNonLocalReturn) {
            PartialLinkageCase.IllegalNonLocalReturn illegalNonLocalReturn = (PartialLinkageCase.IllegalNonLocalReturn) partialLinkageCase;
            illegalNonLocalReturn(sb, illegalNonLocalReturn.getExpression(), illegalNonLocalReturn.getValidReturnTargets());
        } else if (partialLinkageCase instanceof PartialLinkageCase.ExpressionHasInaccessibleDeclaration) {
            expression(sb, ((PartialLinkageCase.ExpressionHasInaccessibleDeclaration) partialLinkageCase).getExpression(), new Function1<ExpressionKind, Appendable>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageErrorMessagesKt$renderLinkageError$1$7
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Appendable invoke(ExpressionKind it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return PartialLinkageErrorMessagesKt.inaccessibleDeclaration(sb, ((PartialLinkageCase.ExpressionHasInaccessibleDeclaration) partialLinkageCase).getReferencedDeclarationSymbol(), ((PartialLinkageCase.ExpressionHasInaccessibleDeclaration) partialLinkageCase).getDeclaringModule(), ((PartialLinkageCase.ExpressionHasInaccessibleDeclaration) partialLinkageCase).getUseSiteModule());
                }
            });
        } else if (partialLinkageCase instanceof PartialLinkageCase.InvalidConstructorDelegation) {
            PartialLinkageCase.InvalidConstructorDelegation invalidConstructorDelegation = (PartialLinkageCase.InvalidConstructorDelegation) partialLinkageCase;
            invalidConstructorDelegation(sb, invalidConstructorDelegation.getConstructorSymbol(), invalidConstructorDelegation.getSuperClassSymbol(), invalidConstructorDelegation.getUnexpectedSuperClassConstructorSymbol());
        } else if (partialLinkageCase instanceof PartialLinkageCase.AbstractClassInstantiation) {
            expression(sb, ((PartialLinkageCase.AbstractClassInstantiation) partialLinkageCase).getConstructorCall(), new Function1<ExpressionKind, Appendable>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageErrorMessagesKt$renderLinkageError$1$8
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Appendable invoke(ExpressionKind it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return PartialLinkageErrorMessagesKt.cantInstantiateAbstractClass(sb, ((PartialLinkageCase.AbstractClassInstantiation) partialLinkageCase).getClassSymbol());
                }
            });
        } else if (partialLinkageCase instanceof PartialLinkageCase.UnimplementedAbstractCallable) {
            unimplementedAbstractCallable(sb, ((PartialLinkageCase.UnimplementedAbstractCallable) partialLinkageCase).getCallable());
        } else if (partialLinkageCase instanceof PartialLinkageCase.UnusableAnnotation) {
            PartialLinkageCase.UnusableAnnotation unusableAnnotation = (PartialLinkageCase.UnusableAnnotation) partialLinkageCase;
            unusableAnnotation(sb, unusableAnnotation.getAnnotationConstructorSymbol(), unusableAnnotation.getHolderDeclarationSymbol());
        } else if (partialLinkageCase instanceof PartialLinkageCase.AmbiguousNonOverriddenCallable) {
            ambiguousNonOverriddenCallable(sb, ((PartialLinkageCase.AmbiguousNonOverriddenCallable) partialLinkageCase).getCallable());
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DeclarationKind getDeclarationKind(IrSymbol irSymbol) {
        if (irSymbol instanceof IrClassSymbol) {
            IrClassSymbol irClassSymbol = (IrClassSymbol) irSymbol;
            switch (WhenMappings.$EnumSwitchMapping$0[irClassSymbol.getOwner().getKind().ordinal()]) {
                case 1:
                    if (irClassSymbol.getOwner().getIsData()) {
                        return DeclarationKind.DATA_CLASS;
                    }
                    if (IrUtilsKt.isAnonymousObject(irClassSymbol.getOwner())) {
                        return DeclarationKind.ANONYMOUS_OBJECT;
                    }
                    if (irClassSymbol.getOwner().getIsInner()) {
                        return DeclarationKind.INNER_CLASS;
                    }
                    return irClassSymbol.getOwner().getIsValue() ? DeclarationKind.VALUE_CLASS : DeclarationKind.CLASS;
                case 2:
                    return irClassSymbol.getOwner().getIsFun() ? DeclarationKind.FUN_INTERFACE : DeclarationKind.INTERFACE;
                case 3:
                    return DeclarationKind.ENUM_CLASS;
                case 4:
                    return DeclarationKind.ENUM_ENTRY_CLASS;
                case 5:
                    return DeclarationKind.ANNOTATION_CLASS;
                case 6:
                    return irClassSymbol.getOwner().getIsCompanion() ? DeclarationKind.COMPANION_OBJECT : DeclarationKind.OBJECT;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }
        if (irSymbol instanceof IrEnumEntrySymbol) {
            return DeclarationKind.ENUM_ENTRY;
        }
        if (irSymbol instanceof IrVariableSymbol) {
            return DeclarationKind.VARIABLE;
        }
        if (irSymbol instanceof IrValueParameterSymbol) {
            return DeclarationKind.VALUE_PARAMETER;
        }
        if (irSymbol instanceof IrFieldSymbol) {
            return ((IrFieldSymbol) irSymbol).getOwner().getCorrespondingPropertySymbol() != null ? DeclarationKind.FIELD_OF_PROPERTY : DeclarationKind.FIELD;
        }
        if (irSymbol instanceof IrPropertySymbol) {
            return DeclarationKind.PROPERTY;
        }
        if (irSymbol instanceof IrSimpleFunctionSymbol) {
            IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) irSymbol;
            if (irSimpleFunctionSymbol.getOwner().getCorrespondingPropertySymbol() != null || (irSymbol.getSignature() instanceof IdSignature.AccessorSignature)) {
                return DeclarationKind.PROPERTY_ACCESSOR;
            }
            return Intrinsics.areEqual(irSimpleFunctionSymbol.getOwner().getOrigin(), IrDeclarationOrigin.LOCAL_FUNCTION_FOR_LAMBDA.INSTANCE) ? DeclarationKind.LAMBDA : DeclarationKind.FUNCTION;
        }
        if (irSymbol instanceof IrConstructorSymbol) {
            return DeclarationKind.CONSTRUCTOR;
        }
        return irSymbol instanceof IrTypeParameterSymbol ? DeclarationKind.TYPE_PARAMETER : DeclarationKind.OTHER_DECLARATION;
    }

    private static final Expression getExpression(IrExpression irExpression) {
        if (irExpression instanceof IrDeclarationReference) {
            if (irExpression instanceof IrFunctionReference) {
                return new Expression(ExpressionKind.REFERENCE, getDeclarationKind(((IrFunctionReference) irExpression).getSymbol()));
            }
            if (irExpression instanceof IrPropertyReference ? true : irExpression instanceof IrLocalDelegatedPropertyReference) {
                return new Expression(ExpressionKind.REFERENCE, DeclarationKind.PROPERTY);
            }
            if (irExpression instanceof IrCall) {
                return new Expression(ExpressionKind.CALLING, getDeclarationKind(((IrCall) irExpression).getSymbol()));
            }
            if (irExpression instanceof IrConstructorCall ? true : irExpression instanceof IrEnumConstructorCall ? true : irExpression instanceof IrDelegatingConstructorCall) {
                return new Expression(ExpressionKind.CALLING, DeclarationKind.CONSTRUCTOR);
            }
            if (irExpression instanceof IrClassReference) {
                return new Expression(ExpressionKind.REFERENCE, getDeclarationKind(((IrClassReference) irExpression).getSymbol()));
            }
            if (irExpression instanceof IrGetField) {
                return new Expression(ExpressionKind.READING, getDeclarationKind(((IrGetField) irExpression).getSymbol()));
            }
            if (irExpression instanceof IrSetField) {
                return new Expression(ExpressionKind.WRITING, getDeclarationKind(((IrSetField) irExpression).getSymbol()));
            }
            if (irExpression instanceof IrGetValue) {
                return new Expression(ExpressionKind.READING, getDeclarationKind(((IrGetValue) irExpression).getSymbol()));
            }
            if (irExpression instanceof IrSetValue) {
                return new Expression(ExpressionKind.WRITING, getDeclarationKind(((IrSetValue) irExpression).getSymbol()));
            }
            return irExpression instanceof IrGetSingletonValue ? new Expression(ExpressionKind.GETTING_INSTANCE, getDeclarationKind(((IrDeclarationReference) irExpression).getSymbol())) : new Expression(ExpressionKind.REFERENCE, DeclarationKind.OTHER_DECLARATION);
        }
        if (irExpression instanceof IrInstanceInitializerCall) {
            return new Expression(ExpressionKind.CALLING_INSTANCE_INITIALIZER, getDeclarationKind(((IrInstanceInitializerCall) irExpression).getClassSymbol()));
        }
        if (irExpression instanceof IrTypeOperatorCall) {
            if (((IrTypeOperatorCall) irExpression).getOperator() == IrTypeOperator.SAM_CONVERSION) {
                return new Expression(ExpressionKind.SAM_CONVERSION, null);
            }
            return new Expression(ExpressionKind.TYPE_OPERATOR, null);
        }
        if ((irExpression instanceof IrBlock) && Intrinsics.areEqual(((IrBlock) irExpression).getOrigin(), IrStatementOrigin.OBJECT_LITERAL.INSTANCE)) {
            return new Expression(ExpressionKind.ANONYMOUS_OBJECT_LITERAL, null);
        }
        return new Expression(ExpressionKind.OTHER_EXPRESSION, null);
    }

    private static final boolean guessName$isCompanionWithDefaultName(IrElement irElement) {
        if (irElement instanceof IrClass) {
            IrClass irClass = (IrClass) irElement;
            if (irClass.getIsCompanion() && Intrinsics.areEqual(irClass.getName(), SpecialNames.DEFAULT_NAME_FOR_COMPANION_OBJECT)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:6:0x000d  */
    private static final String guessName(IrSymbol irSymbol) {
        List listListOf;
        IrProperty owner;
        int i;
        IdSignature signature = irSymbol.getSignature();
        if (signature != null) {
            if (signature instanceof IdSignature.AccessorSignature) {
                i = 2;
            } else if (irSymbol instanceof IrConstructorSymbol) {
                if (guessName$isCompanionWithDefaultName(((IrConstructorSymbol) irSymbol).getOwner().getParent())) {
                    i = 3;
                } else {
                    i = 2;
                }
            } else if ((irSymbol instanceof IrEnumEntrySymbol) || guessName$isCompanionWithDefaultName(irSymbol.getOwner())) {
                i = 2;
            } else {
                i = 1;
            }
            String strGuessName = PartialLinkageUtils.INSTANCE.guessName(signature, i);
            if (strGuessName != null) {
                return strGuessName;
            }
        }
        IrSymbolOwner owner2 = irSymbol.getOwner();
        Name name2 = null;
        IrDeclarationWithName irDeclarationWithName = owner2 instanceof IrDeclarationWithName ? (IrDeclarationWithName) owner2 : null;
        if (irDeclarationWithName == null) {
            return null;
        }
        if (irDeclarationWithName instanceof IrSimpleFunction) {
            Name[] nameArr = new Name[2];
            IrPropertySymbol correspondingPropertySymbol = ((IrSimpleFunction) irDeclarationWithName).getCorrespondingPropertySymbol();
            if (correspondingPropertySymbol != null && (owner = correspondingPropertySymbol.getOwner()) != null) {
                name2 = owner.getName();
            }
            nameArr[0] = name2;
            nameArr[1] = irDeclarationWithName.getName();
            listListOf = CollectionsKt.listOfNotNull((Object[]) nameArr);
        } else if (irDeclarationWithName instanceof IrConstructor) {
            IrClass parentClassOrNull = IrUtilsKt.getParentClassOrNull(irDeclarationWithName);
            if (parentClassOrNull == null || IrUtilsKt.isAnonymousObject(parentClassOrNull)) {
                listListOf = CollectionsKt.listOf(irDeclarationWithName.getName());
            } else if (guessName$isCompanionWithDefaultName(parentClassOrNull)) {
                Name[] nameArr2 = new Name[3];
                IrClass parentClassOrNull2 = IrUtilsKt.getParentClassOrNull(parentClassOrNull);
                nameArr2[0] = parentClassOrNull2 != null ? parentClassOrNull2.getName() : null;
                nameArr2[1] = parentClassOrNull.getName();
                nameArr2[2] = irDeclarationWithName.getName();
                listListOf = CollectionsKt.listOfNotNull((Object[]) nameArr2);
            } else {
                listListOf = CollectionsKt.listOf((Object[]) new Name[]{parentClassOrNull.getName(), irDeclarationWithName.getName()});
            }
        } else if (irDeclarationWithName instanceof IrEnumEntry) {
            Name[] nameArr3 = new Name[2];
            IrClass parentClassOrNull3 = IrUtilsKt.getParentClassOrNull(irDeclarationWithName);
            nameArr3[0] = parentClassOrNull3 != null ? parentClassOrNull3.getName() : null;
            nameArr3[1] = irDeclarationWithName.getName();
            listListOf = CollectionsKt.listOfNotNull((Object[]) nameArr3);
        } else {
            listListOf = CollectionsKt.listOf(irDeclarationWithName.getName());
        }
        return CollectionsKt.joinToString$default(listListOf, ".", null, null, 0, null, null, 62, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v18, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v24 */
    private static final Appendable signature(Appendable appendable, IrSymbol irSymbol) throws IOException {
        String strRender$default;
        PartialLinkageUtils.DeclarationId declarationId;
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        IdSignature signature = irSymbol.getSignature();
        String str = null;
        if (signature == null || (strRender$default = IdSignatureRendererKt.render$default(signature, null, 1, null)) == null) {
            IdSignature privateSignature = irSymbol.getPrivateSignature();
            if (privateSignature == null || (privateSignature instanceof IdSignature.FileSignature)) {
                strRender$default = null;
            } else if (privateSignature instanceof IdSignature.CompositeSignature) {
                IdSignature.CompositeSignature compositeSignature = (IdSignature.CompositeSignature) privateSignature;
                IdSignature container = compositeSignature.getContainer();
                if (container instanceof IdSignature.FileSignature) {
                    String fileName = PathUtil.getFileName(((IdSignature.FileSignature) container).getFileName());
                    Intrinsics.checkNotNullExpressionValue(fileName, "getFileName(...)");
                    boolean z = fileName.length() > 0;
                    T t = fileName;
                    if (!Boolean.valueOf(z).booleanValue()) {
                        t = 0;
                    }
                    if (t == 0) {
                        t = "<unknown file>";
                    }
                    objectRef.element = t;
                    strRender$default = IdSignatureRendererKt.render$default(compositeSignature.getInner(), null, 1, null);
                } else {
                    strRender$default = IdSignatureRendererKt.render$default(privateSignature, null, 1, null);
                }
            } else {
                strRender$default = IdSignatureRendererKt.render$default(privateSignature, null, 1, null);
            }
            if (strRender$default == null) {
                IrSymbolOwner owner = irSymbol.getOwner();
                IrDeclarationWithName irDeclarationWithName = owner instanceof IrDeclarationWithName ? (IrDeclarationWithName) owner : null;
                if (irDeclarationWithName != null && (declarationId = PartialLinkageUtils.DeclarationId.INSTANCE.getDeclarationId(irDeclarationWithName)) != null) {
                    str = declarationId + "|?";
                }
                strRender$default = str == null ? "<unknown symbol>" : str;
            }
        }
        appendable.append('\'').append(strRender$default).append('\'');
        if (objectRef.element != 0) {
            appendable.append(" declared in file ").append((CharSequence) objectRef.element);
        }
        return appendable;
    }

    private static final Appendable declarationName(Appendable appendable, IrSymbol irSymbol) throws IOException {
        Appendable appendableAppend = appendable.append('\'');
        String strGuessName = guessName(irSymbol);
        if (strGuessName == null) {
            strGuessName = PartialLinkageUtils.INSTANCE.getUNKNOWN_NAME().asString();
            Intrinsics.checkNotNullExpressionValue(strGuessName, "asString(...)");
        }
        Appendable appendableAppend2 = appendableAppend.append(strGuessName).append('\'');
        Intrinsics.checkNotNullExpressionValue(appendableAppend2, "append(...)");
        return appendableAppend2;
    }

    private static final Appendable declarationKind(Appendable appendable, IrSymbol irSymbol, boolean z) {
        return appendCapitalized(appendable, getDeclarationKind(irSymbol).getDisplayName(), z);
    }

    private static final Appendable declarationKindName(Appendable appendable, IrSymbol irSymbol, boolean z) throws IOException {
        IrSymbol symbol;
        DeclarationKind declarationKind = getDeclarationKind(irSymbol);
        appendCapitalized(appendable, declarationKind.getDisplayName(), z);
        int i = WhenMappings.$EnumSwitchMapping$1[declarationKind.ordinal()];
        if (i == 1) {
            return appendable;
        }
        if (i == 2) {
            IrSymbolOwner owner = irSymbol.getOwner();
            IrDeclaration irDeclaration = owner instanceof IrDeclaration ? (IrDeclaration) owner : null;
            IrDeclarationParent parent = irDeclaration != null ? irDeclaration.getParent() : null;
            IrDeclaration irDeclaration2 = parent instanceof IrDeclaration ? (IrDeclaration) parent : null;
            if (irDeclaration2 != null && (symbol = irDeclaration2.getSymbol()) != null) {
                Appendable appendableAppend = appendable.append(" in ");
                Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
                return declarationKindName(appendableAppend, symbol, false);
            }
        }
        Appendable appendableAppend2 = appendable.append(" ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend2, "append(...)");
        return declarationName(appendableAppend2, irSymbol);
    }

    private static final Appendable sortedDeclarationsKindName(Appendable appendable, Set<? extends IrSymbol> set) throws IOException {
        Set<? extends IrSymbol> set2 = set;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set2, 10));
        for (IrSymbol irSymbol : set2) {
            StringBuilder sb = new StringBuilder();
            declarationKindName(sb, irSymbol, false);
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            arrayList.add(string);
        }
        CollectionsKt.joinTo(CollectionsKt.sorted(arrayList), appendable, (112 & 2) != 0 ? ", " : null, (112 & 4) != 0 ? "" : null, (112 & 8) != 0 ? "" : null, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : null);
        return appendable;
    }

    private static final Appendable sortedDeclarationsName(Appendable appendable, Set<? extends IrSymbol> set) throws IOException {
        Set<? extends IrSymbol> set2 = set;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set2, 10));
        for (IrSymbol irSymbol : set2) {
            StringBuilder sb = new StringBuilder();
            declarationName(sb, irSymbol);
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            arrayList.add(string);
        }
        CollectionsKt.joinTo(CollectionsKt.sorted(arrayList), appendable, (112 & 2) != 0 ? ", " : null, (112 & 4) != 0 ? "" : null, (112 & 8) != 0 ? "" : null, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : null);
        return appendable;
    }

    private static final Appendable declarationNameIsKind(Appendable appendable, IrSymbol irSymbol) throws IOException {
        Appendable appendableAppend = declarationName(appendable, irSymbol).append(" is ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        return declarationKind(appendableAppend, irSymbol, false);
    }

    private static final Appendable module(Appendable appendable, org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module module) throws IOException {
        Appendable appendableAppend = appendable.append("module ").append(module.getName());
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        return appendableAppend;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Appendable unusableClassifier(Appendable appendable, ExploredClassifier.Unusable unusable, CauseRendering causeRendering, boolean z) throws IOException {
        Pair pair;
        if (unusable instanceof ExploredClassifier.Unusable.CanBeRootCause) {
            pair = TuplesKt.to(unusable, null);
        } else {
            if (!(unusable instanceof ExploredClassifier.Unusable.DueToOtherClassifier)) {
                throw new NoWhenBranchMatchedException();
            }
            pair = TuplesKt.to(((ExploredClassifier.Unusable.DueToOtherClassifier) unusable).getRootCause(), unusable);
        }
        ExploredClassifier.Unusable.CanBeRootCause canBeRootCause = (ExploredClassifier.Unusable.CanBeRootCause) pair.component1();
        ExploredClassifier.Unusable.DueToOtherClassifier dueToOtherClassifier = (ExploredClassifier.Unusable.DueToOtherClassifier) pair.component2();
        if (canBeRootCause instanceof ExploredClassifier.Unusable.MissingClassifier) {
            if (Intrinsics.areEqual(causeRendering, CauseRendering.Standalone.INSTANCE)) {
                noDeclarationForSymbol(appendable, canBeRootCause.getSymbol());
            } else if (causeRendering instanceof CauseRendering.UsedFromSomewhere) {
                Appendable appendableAppend = unusableClassifier$objectUses((CauseRendering.UsedFromSomewhere) causeRendering, appendable).append("unlinked ");
                Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
                Appendable appendableAppend2 = unusableClassifier$subjectKind(appendableAppend, canBeRootCause, false).append(" symbol ");
                Intrinsics.checkNotNullExpressionValue(appendableAppend2, "append(...)");
                unusableClassifier$via(signature(appendableAppend2, canBeRootCause.getSymbol()), z, dueToOtherClassifier);
            }
        } else if (canBeRootCause instanceof ExploredClassifier.Unusable.InvalidInheritance) {
            ExploredClassifier.Unusable.InvalidInheritance invalidInheritance = (ExploredClassifier.Unusable.InvalidInheritance) canBeRootCause;
            if (invalidInheritance.getSuperClassSymbols().size() == 1) {
                if (Intrinsics.areEqual(causeRendering, CauseRendering.Standalone.INSTANCE)) {
                    unusableClassifier$inheritsFromSuperClass(unusableClassifier$subject(appendable, canBeRootCause, true), canBeRootCause);
                } else if (causeRendering instanceof CauseRendering.UsedFromSomewhere) {
                    CauseRendering.UsedFromSomewhere usedFromSomewhere = (CauseRendering.UsedFromSomewhere) causeRendering;
                    if (Intrinsics.areEqual(usedFromSomewhere.getObjectSymbol(), invalidInheritance.getSymbol())) {
                        Appendable appendableUnusableClassifier$object = unusableClassifier$object(usedFromSomewhere, appendable);
                        Intrinsics.checkNotNullExpressionValue(appendableUnusableClassifier$object, "unusableClassifier$object(...)");
                        unusableClassifier$inheritsFromSuperClass(appendableUnusableClassifier$object, canBeRootCause);
                    } else {
                        Appendable appendableUnusableClassifier$objectUses = unusableClassifier$objectUses(usedFromSomewhere, appendable);
                        Intrinsics.checkNotNullExpressionValue(appendableUnusableClassifier$objectUses, "unusableClassifier$objectUses(...)");
                        Appendable appendableAppend3 = unusableClassifier$via(unusableClassifier$subject(appendableUnusableClassifier$objectUses, canBeRootCause, false), z, dueToOtherClassifier).append(" that");
                        Intrinsics.checkNotNullExpressionValue(appendableAppend3, "append(...)");
                        unusableClassifier$inheritsFromSuperClass(appendableAppend3, canBeRootCause);
                    }
                }
            } else if (Intrinsics.areEqual(causeRendering, CauseRendering.Standalone.INSTANCE)) {
                unusableClassifier$inheritsFromMultipleClasses(unusableClassifier$subject(appendable, canBeRootCause, true), canBeRootCause);
            } else if (causeRendering instanceof CauseRendering.UsedFromSomewhere) {
                CauseRendering.UsedFromSomewhere usedFromSomewhere2 = (CauseRendering.UsedFromSomewhere) causeRendering;
                if (Intrinsics.areEqual(usedFromSomewhere2.getObjectSymbol(), invalidInheritance.getSymbol())) {
                    Appendable appendableUnusableClassifier$object2 = unusableClassifier$object(usedFromSomewhere2, appendable);
                    Intrinsics.checkNotNullExpressionValue(appendableUnusableClassifier$object2, "unusableClassifier$object(...)");
                    unusableClassifier$inheritsFromMultipleClasses(appendableUnusableClassifier$object2, canBeRootCause);
                } else {
                    Appendable appendableUnusableClassifier$objectUses2 = unusableClassifier$objectUses(usedFromSomewhere2, appendable);
                    Intrinsics.checkNotNullExpressionValue(appendableUnusableClassifier$objectUses2, "unusableClassifier$objectUses(...)");
                    Appendable appendableAppend4 = unusableClassifier$via(unusableClassifier$subject(appendableUnusableClassifier$objectUses2, canBeRootCause, false), z, dueToOtherClassifier).append(" that");
                    Intrinsics.checkNotNullExpressionValue(appendableAppend4, "append(...)");
                    unusableClassifier$inheritsFromMultipleClasses(appendableAppend4, canBeRootCause);
                }
            }
        } else if (canBeRootCause instanceof ExploredClassifier.Unusable.AnnotationWithUnacceptableParameter) {
            if (Intrinsics.areEqual(causeRendering, CauseRendering.Standalone.INSTANCE)) {
                Appendable appendableAppend5 = unusableClassifier$subject(appendable, canBeRootCause, true).append(" has ");
                Intrinsics.checkNotNullExpressionValue(appendableAppend5, "append(...)");
                unusableClassifier$unacceptableClassifier(appendableAppend5, canBeRootCause);
            } else if (causeRendering instanceof CauseRendering.UsedFromSomewhere) {
                CauseRendering.UsedFromSomewhere usedFromSomewhere3 = (CauseRendering.UsedFromSomewhere) causeRendering;
                if (Intrinsics.areEqual(usedFromSomewhere3.getObjectSymbol(), ((ExploredClassifier.Unusable.AnnotationWithUnacceptableParameter) canBeRootCause).getSymbol())) {
                    Appendable appendableUnusableClassifier$objectUses3 = unusableClassifier$objectUses(usedFromSomewhere3, appendable);
                    Intrinsics.checkNotNullExpressionValue(appendableUnusableClassifier$objectUses3, "unusableClassifier$objectUses(...)");
                    unusableClassifier$via(unusableClassifier$unacceptableClassifier(appendableUnusableClassifier$objectUses3, canBeRootCause), z, dueToOtherClassifier);
                } else {
                    Appendable appendableUnusableClassifier$objectUses4 = unusableClassifier$objectUses(usedFromSomewhere3, appendable);
                    Intrinsics.checkNotNullExpressionValue(appendableUnusableClassifier$objectUses4, "unusableClassifier$objectUses(...)");
                    Appendable appendableAppend6 = unusableClassifier$via(unusableClassifier$subject(appendableUnusableClassifier$objectUses4, canBeRootCause, false), z, dueToOtherClassifier).append(" that has ");
                    Intrinsics.checkNotNullExpressionValue(appendableAppend6, "append(...)");
                    unusableClassifier$unacceptableClassifier(appendableAppend6, canBeRootCause);
                }
            }
        }
        return appendable;
    }

    private static final Appendable unusableClassifier$object(CauseRendering.UsedFromSomewhere usedFromSomewhere, Appendable appendable) {
        return appendable.append(usedFromSomewhere.getObjectText());
    }

    private static final Appendable unusableClassifier$objectUses(CauseRendering.UsedFromSomewhere usedFromSomewhere, Appendable appendable) {
        return unusableClassifier$object(usedFromSomewhere, appendable).append(" uses ");
    }

    private static final Appendable unusableClassifier$subject(Appendable appendable, ExploredClassifier.Unusable.CanBeRootCause canBeRootCause, boolean z) {
        return declarationKindName(appendable, canBeRootCause.getSymbol(), z);
    }

    private static final Appendable unusableClassifier$subjectKind(Appendable appendable, ExploredClassifier.Unusable.CanBeRootCause canBeRootCause, boolean z) {
        return declarationKind(appendable, canBeRootCause.getSymbol(), z);
    }

    private static final Appendable unusableClassifier$via(Appendable appendable, boolean z, ExploredClassifier.Unusable.DueToOtherClassifier dueToOtherClassifier) throws IOException {
        if (!z || dueToOtherClassifier == null) {
            return appendable;
        }
        Appendable appendableAppend = appendable.append(" (via ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        return declarationKindName(appendableAppend, dueToOtherClassifier.getSymbol(), false).append(Constant.AFTER_QUTO);
    }

    private static final Appendable unusableClassifier$inheritsFromSuperClass(Appendable appendable, ExploredClassifier.Unusable.CanBeRootCause canBeRootCause) throws IOException {
        IrClassSymbol irClassSymbol = (IrClassSymbol) CollectionsKt.single(((ExploredClassifier.Unusable.InvalidInheritance) canBeRootCause).getSuperClassSymbols());
        if (irClassSymbol.getOwner().getModality() == Modality.FINAL) {
            appendable.append(" inherits from final ");
        } else {
            appendable.append(" has illegal inheritance from ");
        }
        return declarationKindName(appendable, irClassSymbol, false);
    }

    private static final Appendable unusableClassifier$inheritsFromMultipleClasses(Appendable appendable, ExploredClassifier.Unusable.CanBeRootCause canBeRootCause) throws IOException {
        ExploredClassifier.Unusable.InvalidInheritance invalidInheritance = (ExploredClassifier.Unusable.InvalidInheritance) canBeRootCause;
        ArrayList arrayList = new ArrayList(invalidInheritance.getSuperClassSymbols().size());
        for (IrClassSymbol irClassSymbol : invalidInheritance.getSuperClassSymbols()) {
            StringBuilder sb = new StringBuilder();
            declarationName(sb, irClassSymbol);
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            arrayList.add(string);
        }
        CollectionsKt.sort(arrayList);
        appendable.append(" simultaneously inherits from ").append(String.valueOf(arrayList.size())).append(" classes: ");
        CollectionsKt.joinTo(arrayList, appendable, (112 & 2) != 0 ? ", " : null, (112 & 4) != 0 ? "" : null, (112 & 8) != 0 ? "" : null, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : null);
        return appendable;
    }

    private static final Appendable unusableClassifier$unacceptableClassifier(Appendable appendable, ExploredClassifier.Unusable.CanBeRootCause canBeRootCause) throws IOException {
        Appendable appendableAppend = appendable.append("non-annotation ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        Appendable appendableAppend2 = declarationKindName(appendableAppend, ((ExploredClassifier.Unusable.AnnotationWithUnacceptableParameter) canBeRootCause).getUnacceptableClassifierSymbol(), false).append(" as a parameter");
        Intrinsics.checkNotNullExpressionValue(appendableAppend2, "append(...)");
        return appendableAppend2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Appendable noDeclarationForSymbol(Appendable appendable, IrSymbol irSymbol) throws IOException {
        Appendable appendableAppend = appendable.append("No ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        Appendable appendableAppend2 = declarationKind(appendableAppend, irSymbol, false).append(" found for symbol ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend2, "append(...)");
        return signature(appendableAppend2, irSymbol);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Appendable declarationWithUnusableClassifier(Appendable appendable, IrSymbol irSymbol, ExploredClassifier.Unusable unusable, boolean z) throws IOException {
        IrSymbolOwner owner = irSymbol.getOwner();
        IrFunction irFunction = owner instanceof IrFunction ? (IrFunction) owner : null;
        IrDeclarationParent parent = irFunction != null ? irFunction.getParent() : null;
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        boolean zAreEqual = Intrinsics.areEqual(irClass != null ? irClass.getSymbol() : null, unusable.getSymbol());
        IrClassifierSymbol symbol = zAreEqual ? unusable.getSymbol() : irSymbol;
        StringBuilder sb = new StringBuilder();
        if (zAreEqual) {
            boolean z2 = irFunction instanceof IrConstructor;
            if (!z) {
                Appendable appendableAppend = declarationKindName(sb, symbol, true).append(z2 ? " created by " : ", the dispatch receiver of ");
                Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
                declarationKindName(appendableAppend, irSymbol, false);
            } else if (z2) {
                declarationKindName(sb, symbol, true);
            } else {
                StringBuilder sbAppend = sb.append("Dispatch receiver ");
                Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
                declarationKindName(sbAppend, symbol, false);
            }
        } else if (z) {
            declarationKind(sb, symbol, true);
        } else {
            declarationKindName(sb, symbol, true);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return unusableClassifier(appendable, unusable, new CauseRendering.UsedFromDeclaration(string, symbol), !zAreEqual);
    }

    private static final Appendable expressionWithUnusableClassifier(final StringBuilder sb, final IrExpression irExpression, final ExploredClassifier.Unusable unusable) {
        return expression(sb, irExpression, new Function1<ExpressionKind, Appendable>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageErrorMessagesKt.expressionWithUnusableClassifier.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Code duplicated, block: B:22:0x0060  */
            @Override // kotlin.jvm.functions.Function1
            public final Appendable invoke(ExpressionKind expressionKind) {
                Intrinsics.checkNotNullParameter(expressionKind, "expressionKind");
                IrExpression irExpression2 = irExpression;
                boolean z = false;
                if (irExpression2 instanceof IrGetSingletonValue) {
                    IrSymbol symbol = ((IrGetSingletonValue) irExpression2).getSymbol();
                    if (symbol instanceof IrEnumEntrySymbol) {
                        IrDeclarationParent parent = ((IrEnumEntrySymbol) symbol).getOwner().getParent();
                        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
                        if (!Intrinsics.areEqual(irClass != null ? irClass.getSymbol() : null, unusable.getSymbol())) {
                            z = true;
                        }
                    } else if (!Intrinsics.areEqual(symbol, unusable.getSymbol())) {
                        z = true;
                    }
                } else if (expressionKind != ExpressionKind.ANONYMOUS_OBJECT_LITERAL || PartialLinkageErrorMessagesKt.getDeclarationKind(unusable.getSymbol()) != DeclarationKind.ANONYMOUS_OBJECT) {
                    z = true;
                }
                return PartialLinkageErrorMessagesKt.unusableClassifier(sb, unusable, CauseRendering.UsedFromExpression.INSTANCE, z);
            }
        });
    }

    private static final Appendable expression(StringBuilder sb, IrExpression irExpression, Function1<? super ExpressionKind, ? extends Appendable> function1) throws IOException {
        Expression expression = getExpression(irExpression);
        ExpressionKind kind = expression.getKind();
        DeclarationKind referencedDeclarationKind = expression.getReferencedDeclarationKind();
        boolean z = kind.getPrefix() != null;
        if (z) {
            sb.append(kind.getPrefix());
        }
        if (referencedDeclarationKind != null) {
            if (z) {
                sb.append(" ");
            }
            if (irExpression instanceof IrGetSingletonValue) {
                Appendable appendableAppend = appendCapitalized(sb, "singleton", !z).append(" ");
                Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
                declarationName(appendableAppend, ((IrGetSingletonValue) irExpression).getSymbol());
            } else if (irExpression instanceof IrDeclarationReference) {
                declarationKindName(sb, ((IrDeclarationReference) irExpression).getSymbol(), !z);
            } else if (irExpression instanceof IrInstanceInitializerCall) {
                declarationKindName(sb, ((IrInstanceInitializerCall) irExpression).getClassSymbol(), !z);
            } else {
                appendCapitalized(sb, referencedDeclarationKind.getDisplayName(), !z);
            }
        }
        String postfix = kind.getPostfix();
        if (postfix != null) {
            sb.append(" ").append(postfix);
        }
        sb.append(": ");
        return function1.invoke(kind);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Appendable wrongTypeOfDeclaration(Appendable appendable, IrSymbol irSymbol, String str) throws IOException {
        Appendable appendableAppend = declarationNameIsKind(appendable, irSymbol).append(" while ").append(str).append(" is expected");
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        return appendableAppend;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Appendable memberAccessExpressionArgumentsMismatch(Appendable appendable, IrFunctionSymbol irFunctionSymbol, boolean z, boolean z2, int i, int i2) throws IOException {
        if (z && !z2) {
            Appendable appendableAppend = appendable.append("The call site provides excessive dispatch receiver parameter 'this' that is not needed for the ");
            Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
            return declarationKind(appendableAppend, irFunctionSymbol, false);
        }
        if (!z && z2) {
            Appendable appendableAppend2 = appendable.append("The call site does not provide a dispatch receiver parameter 'this' that the ");
            Intrinsics.checkNotNullExpressionValue(appendableAppend2, "append(...)");
            Appendable appendableAppend3 = declarationKind(appendableAppend2, irFunctionSymbol, false).append(" requires");
            Intrinsics.checkNotNullExpressionValue(appendableAppend3, "append(...)");
            return appendableAppend3;
        }
        Appendable appendableAppend4 = appendable.append("The call site provides ").append(i > i2 ? "more" : BuiltInOperatorNames.LESS).append(" value arguments (").append(String.valueOf(i)).append(") than the ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend4, "append(...)");
        Appendable appendableAppend5 = declarationKind(appendableAppend4, irFunctionSymbol, false).append(" requires (").append(String.valueOf(i2)).append(Constant.AFTER_QUTO);
        Intrinsics.checkNotNullExpressionValue(appendableAppend5, "append(...)");
        return appendableAppend5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Appendable invalidSamConversion(Appendable appendable, IrTypeOperatorCall irTypeOperatorCall, Set<? extends IrSimpleFunctionSymbol> set, IrPropertySymbol irPropertySymbol) throws IOException {
        declarationKindName(appendable, IrTypesKt.getClassifierOrFail(irTypeOperatorCall.getTypeOperand()), true);
        if (irPropertySymbol != null) {
            Appendable appendableAppend = appendable.append(" has abstract ");
            Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
            return declarationKindName(appendableAppend, irPropertySymbol, false);
        }
        if (set.isEmpty()) {
            Appendable appendableAppend2 = appendable.append(" does not have an abstract function");
            Intrinsics.checkNotNullExpressionValue(appendableAppend2, "append(...)");
            return appendableAppend2;
        }
        Appendable appendableAppend3 = appendable.append(" has more than one abstract function: ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend3, "append(...)");
        return sortedDeclarationsName(appendableAppend3, set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Appendable suspendableCallWithoutCoroutine(Appendable appendable) throws IOException {
        Appendable appendableAppend = appendable.append("Suspend function can be called only from a coroutine or another suspend function");
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        return appendableAppend;
    }

    private static final Appendable illegalNonLocalReturn(Appendable appendable, IrReturn irReturn, Set<? extends IrReturnTargetSymbol> set) throws IOException {
        Appendable appendableAppend = appendable.append("Illegal non-local return: The return target is ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        Appendable appendableAppend2 = declarationKindName(appendableAppend, irReturn.getReturnTargetSymbol(), false).append(" while only the following return targets are allowed: ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend2, "append(...)");
        return sortedDeclarationsKindName(appendableAppend2, set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Appendable inaccessibleDeclaration(Appendable appendable, IrSymbol irSymbol, org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module module, org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.Module module2) throws IOException {
        Appendable appendableAppend = appendable.append("Private ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        Appendable appendableAppend2 = declarationKind(appendableAppend, irSymbol, false).append(" declared in ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend2, "append(...)");
        Appendable appendableAppend3 = module(appendableAppend2, module).append(" can not be accessed in ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend3, "append(...)");
        return module(appendableAppend3, module2);
    }

    private static final Appendable invalidConstructorDelegation(Appendable appendable, IrConstructorSymbol irConstructorSymbol, IrClassSymbol irClassSymbol, IrConstructorSymbol irConstructorSymbol2) throws IOException {
        Appendable appendableAppend = declarationKind(appendable, IrUtilsKt.getParentAsClass(irConstructorSymbol.getOwner()).getSymbol(), true).append(" initialization error: ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        Appendable appendableAppend2 = declarationKindName(appendableAppend, irConstructorSymbol, true).append(" should call a constructor of direct super class ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend2, "append(...)");
        Appendable appendableAppend3 = declarationName(appendableAppend2, irClassSymbol).append(" but calls ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend3, "append(...)");
        Appendable appendableAppend4 = declarationName(appendableAppend3, irConstructorSymbol2).append(" instead");
        Intrinsics.checkNotNullExpressionValue(appendableAppend4, "append(...)");
        return appendableAppend4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Appendable cantInstantiateAbstractClass(Appendable appendable, IrClassSymbol irClassSymbol) throws IOException {
        Appendable appendableAppend = appendable.append("Can not instantiate ");
        String lowerCase = irClassSymbol.getOwner().getModality().name().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        Appendable appendableAppend2 = appendableAppend.append(lowerCase).append(" ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend2, "append(...)");
        return declarationKindName(appendableAppend2, irClassSymbol, false);
    }

    private static final Appendable unimplementedAbstractCallable(Appendable appendable, IrOverridableDeclaration<?> irOverridableDeclaration) throws IOException {
        Appendable appendableAppend = appendable.append("Abstract ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        Appendable appendableAppend2 = declarationKindName(appendableAppend, irOverridableDeclaration.getSymbol(), false).append(" is not implemented in non-abstract ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend2, "append(...)");
        return declarationKindName(appendableAppend2, IrUtilsKt.getParentAsClass(irOverridableDeclaration).getSymbol(), false);
    }

    private static final Appendable unusableAnnotation(Appendable appendable, IrConstructorSymbol irConstructorSymbol, IrSymbol irSymbol) throws IOException {
        Appendable appendableAppend = appendable.append("Unusable annotation ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        Appendable appendableAppend2 = declarationName(appendableAppend, irConstructorSymbol).append(" has been removed from ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend2, "append(...)");
        return declarationKindName(appendableAppend2, irSymbol, false);
    }

    private static final Appendable ambiguousNonOverriddenCallable(Appendable appendable, IrOverridableDeclaration<?> irOverridableDeclaration) throws IOException {
        Appendable appendableAppend = declarationKindName(appendable, irOverridableDeclaration.getSymbol(), true).append(" in ");
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        Appendable appendableAppend2 = declarationKindName(appendableAppend, IrUtilsKt.getParentAsClass(irOverridableDeclaration).getSymbol(), false).append(" inherits more than one default implementation");
        Intrinsics.checkNotNullExpressionValue(appendableAppend2, "append(...)");
        return appendableAppend2;
    }

    private static final Appendable appendCapitalized(Appendable appendable, String str, boolean z) throws IOException {
        if (z && str.length() > 0) {
            char cCharAt = str.charAt(0);
            if (Character.isLowerCase(cCharAt)) {
                Appendable appendableAppend = appendable.append(Character.toUpperCase(cCharAt));
                String strSubstring = str.substring(1);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                Appendable appendableAppend2 = appendableAppend.append(strSubstring);
                Intrinsics.checkNotNullExpressionValue(appendableAppend2, "append(...)");
                return appendableAppend2;
            }
        }
        Appendable appendableAppend3 = appendable.append(str);
        Intrinsics.checkNotNullExpressionValue(appendableAppend3, "append(...)");
        return appendableAppend3;
    }
}
