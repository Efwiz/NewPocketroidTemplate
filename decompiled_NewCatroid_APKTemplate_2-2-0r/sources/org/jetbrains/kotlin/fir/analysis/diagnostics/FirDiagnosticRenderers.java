package org.jetbrains.kotlin.fir.analysis.diagnostics;

import io.netty.util.internal.StringUtil;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import name.antonsmirnov.firmata.FormatHelper;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderers;
import org.jetbrains.kotlin.diagnostics.WhenMissingCase;
import org.jetbrains.kotlin.diagnostics.rendering.ContextIndependentParameterRenderer;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRendererKt;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.renderer.ConeIdFullRenderer;
import org.jetbrains.kotlin.fir.renderer.ConeIdShortRenderer;
import org.jetbrains.kotlin.fir.renderer.ConeTypeRenderer;
import org.jetbrains.kotlin.fir.renderer.ConeTypeRendererWithJavaFlexibleTypes;
import org.jetbrains.kotlin.fir.renderer.FirCallNoArgumentsRenderer;
import org.jetbrains.kotlin.fir.renderer.FirDeclarationRenderer;
import org.jetbrains.kotlin.fir.renderer.FirNoClassMemberRenderer;
import org.jetbrains.kotlin.fir.renderer.FirPartialModifierRenderer;
import org.jetbrains.kotlin.fir.renderer.FirRenderer;
import org.jetbrains.kotlin.fir.renderer.FirValueParameterRendererNoDefaultValue;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousObjectSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirement;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirDiagnosticRenderers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R!\u0010\u0003\u001a\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\bR\u001b\u0010\f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\bR\u001b\u0010\u000e\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\bR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\bR\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\bR\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\bR\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\bR\u0019\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001c0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\bR\u0019\u0010\u001e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001f0\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\bR\u001b\u0010!\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\"0\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\bR\u001b\u0010$\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030%0\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\bR\u001d\u0010'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\bR\u0017\u0010*\u001a\b\u0012\u0004\u0012\u00020(0\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010\bR\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020(0\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010\bR\u0017\u0010.\u001a\b\u0012\u0004\u0012\u00020/0\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\bR!\u00101\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b2\u0010\u0002\u001a\u0004\b3\u0010\bR!\u00104\u001a\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b5\u0010\bR!\u00106\u001a\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u0003070\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b8\u0010\bR\u001b\u00109\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030:0\u0004¢\u0006\b\n\u0000\u001a\u0004\b;\u0010\bR\u001d\u0010<\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020>0=0\u0004¢\u0006\b\n\u0000\u001a\u0004\b?\u0010\bR\u000e\u0010@\u001a\u00020AX\u0082T¢\u0006\u0002\n\u0000¨\u0006B"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/diagnostics/FirDiagnosticRenderers;", "", "()V", "AMBIGUOUS_CALLS", "Lorg/jetbrains/kotlin/diagnostics/rendering/ContextIndependentParameterRenderer;", "", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getAMBIGUOUS_CALLS", "()Lorg/jetbrains/kotlin/diagnostics/rendering/ContextIndependentParameterRenderer;", "CALLEE_NAME", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getCALLEE_NAME", "DECLARATION_NAME", "getDECLARATION_NAME", "FQ_NAMES_IN_TYPES", "getFQ_NAMES_IN_TYPES", "FUNCTIONAL_TYPE_KIND", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "getFUNCTIONAL_TYPE_KIND", "FUNCTIONAL_TYPE_KINDS", "getFUNCTIONAL_TYPE_KINDS", "MODULE_DATA", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getMODULE_DATA", "NAME_OF_CONTAINING_DECLARATION_OR_FILE", "Lorg/jetbrains/kotlin/name/CallableId;", "getNAME_OF_CONTAINING_DECLARATION_OR_FILE", "NAME_OF_DECLARATION_OR_FILE", "Lorg/jetbrains/kotlin/name/ClassId;", "getNAME_OF_DECLARATION_OR_FILE", "OPTIONAL_SENTENCE", "", "getOPTIONAL_SENTENCE", "RENDER_CLASS_OR_OBJECT", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "getRENDER_CLASS_OR_OBJECT", "RENDER_CLASS_OR_OBJECT_NAME", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getRENDER_CLASS_OR_OBJECT_NAME", "RENDER_COLLECTION_OF_TYPES", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getRENDER_COLLECTION_OF_TYPES", "RENDER_TYPE", "getRENDER_TYPE", "RENDER_TYPE_WITH_ANNOTATIONS", "getRENDER_TYPE_WITH_ANNOTATIONS", "REQUIRE_KOTLIN_VERSION", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement$Version;", "getREQUIRE_KOTLIN_VERSION", "SYMBOL", "getSYMBOL$annotations", "getSYMBOL", "SYMBOLS", "getSYMBOLS", "SYMBOLS_ON_NEWLINE_WITH_INDENT", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "getSYMBOLS_ON_NEWLINE_WITH_INDENT", "VARIABLE_NAME", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "getVARIABLE_NAME", "WHEN_MISSING_CASES", "", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "getWHEN_MISSING_CASES", "WHEN_MISSING_LIMIT", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDiagnosticRenderers {
    private static final ContextIndependentParameterRenderer<Collection<? extends FirBasedSymbol<?>>> AMBIGUOUS_CALLS;
    private static final ContextIndependentParameterRenderer<FirExpression> CALLEE_NAME;
    private static final ContextIndependentParameterRenderer<FirBasedSymbol<?>> DECLARATION_NAME;
    private static final ContextIndependentParameterRenderer<FirBasedSymbol<?>> FQ_NAMES_IN_TYPES;
    private static final ContextIndependentParameterRenderer<FunctionTypeKind> FUNCTIONAL_TYPE_KIND;
    private static final ContextIndependentParameterRenderer<Collection<? extends FunctionTypeKind>> FUNCTIONAL_TYPE_KINDS;
    public static final FirDiagnosticRenderers INSTANCE = new FirDiagnosticRenderers();
    private static final ContextIndependentParameterRenderer<FirModuleData> MODULE_DATA;
    private static final ContextIndependentParameterRenderer<CallableId> NAME_OF_CONTAINING_DECLARATION_OR_FILE;
    private static final ContextIndependentParameterRenderer<ClassId> NAME_OF_DECLARATION_OR_FILE;
    private static final ContextIndependentParameterRenderer<String> OPTIONAL_SENTENCE;
    private static final ContextIndependentParameterRenderer<FirClassSymbol<?>> RENDER_CLASS_OR_OBJECT;
    private static final ContextIndependentParameterRenderer<FirClassLikeSymbol<?>> RENDER_CLASS_OR_OBJECT_NAME;
    private static final ContextIndependentParameterRenderer<Collection<? extends ConeKotlinType>> RENDER_COLLECTION_OF_TYPES;
    private static final ContextIndependentParameterRenderer<ConeKotlinType> RENDER_TYPE;
    private static final ContextIndependentParameterRenderer<ConeKotlinType> RENDER_TYPE_WITH_ANNOTATIONS;
    private static final ContextIndependentParameterRenderer<VersionRequirement.Version> REQUIRE_KOTLIN_VERSION;
    private static final ContextIndependentParameterRenderer<FirBasedSymbol<?>> SYMBOL;
    private static final ContextIndependentParameterRenderer<Collection<? extends FirBasedSymbol<?>>> SYMBOLS;
    private static final ContextIndependentParameterRenderer<Collection<? extends FirCallableSymbol<?>>> SYMBOLS_ON_NEWLINE_WITH_INDENT;
    private static final ContextIndependentParameterRenderer<FirVariableSymbol<?>> VARIABLE_NAME;
    private static final ContextIndependentParameterRenderer<List<? extends WhenMissingCase>> WHEN_MISSING_CASES;

    public static /* synthetic */ void getSYMBOL$annotations() {
    }

    private FirDiagnosticRenderers() {
    }

    static {
        ContextIndependentParameterRenderer<FirBasedSymbol<?>> contextIndependentParameterRendererRenderer = DiagnosticParameterRendererKt.Renderer(new Function1<FirBasedSymbol<?>, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$SYMBOL$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(FirBasedSymbol<?> symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                if (symbol instanceof FirClassLikeSymbol ? true : symbol instanceof FirCallableSymbol) {
                    ConeTypeRenderer coneTypeRenderer = new ConeTypeRenderer();
                    ConeIdShortRenderer coneIdShortRenderer = new ConeIdShortRenderer();
                    FirNoClassMemberRenderer firNoClassMemberRenderer = new FirNoClassMemberRenderer();
                    return new FirRenderer(null, null, null, new FirCallNoArgumentsRenderer(), firNoClassMemberRenderer, null, new FirDeclarationRenderer("local "), coneIdShortRenderer, new FirPartialModifierRenderer(), null, null, null, coneTypeRenderer, null, new FirValueParameterRendererNoDefaultValue(), null, null, 109091, null).renderElementAsString(symbol.getFir(), true);
                }
                if (!(symbol instanceof FirTypeParameterSymbol)) {
                    return "???";
                }
                String strAsString = ((FirTypeParameterSymbol) symbol).getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return strAsString;
            }
        });
        SYMBOL = contextIndependentParameterRendererRenderer;
        SYMBOLS = KtDiagnosticRenderers.INSTANCE.COLLECTION(contextIndependentParameterRendererRenderer);
        SYMBOLS_ON_NEWLINE_WITH_INDENT = new ContextIndependentParameterRenderer<Collection<? extends FirCallableSymbol<?>>>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$SYMBOLS_ON_NEWLINE_WITH_INDENT$1
            private final MultiplatformDiagnosticRenderingMode mode = new MultiplatformDiagnosticRenderingMode();

            @Override // org.jetbrains.kotlin.diagnostics.rendering.ContextIndependentParameterRenderer
            public String render(Collection<? extends FirCallableSymbol<?>> obj) {
                Intrinsics.checkNotNullParameter(obj, "obj");
                StringBuilder sb = new StringBuilder();
                for (FirCallableSymbol<?> firCallableSymbol : obj) {
                    this.mode.newLine(sb);
                    this.mode.renderSymbol(sb, firCallableSymbol, "");
                }
                String string = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        };
        RENDER_COLLECTION_OF_TYPES = DiagnosticParameterRendererKt.Renderer(new Function1<Collection<? extends ConeKotlinType>, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$RENDER_COLLECTION_OF_TYPES$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(Collection<? extends ConeKotlinType> types) {
                Intrinsics.checkNotNullParameter(types, "types");
                return CollectionsKt.joinToString$default(types, ", ", null, null, 0, null, new Function1<ConeKotlinType, CharSequence>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$RENDER_COLLECTION_OF_TYPES$1.1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(ConeKotlinType type) {
                        Intrinsics.checkNotNullParameter(type, "type");
                        return FirDiagnosticRenderers.INSTANCE.getRENDER_TYPE().render(type);
                    }
                }, 30, null);
            }
        });
        CALLEE_NAME = DiagnosticParameterRendererKt.Renderer(new Function1<FirExpression, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$CALLEE_NAME$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(FirExpression element) {
                Intrinsics.checkNotNullParameter(element, "element");
                FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(FirExpressionUtilKt.unwrapSmartcastExpression(element));
                if (calleeReference instanceof FirNamedReference) {
                    String strAsString = ((FirNamedReference) calleeReference).getName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    return strAsString;
                }
                if (calleeReference instanceof FirThisReference) {
                    return "this";
                }
                return calleeReference instanceof FirSuperReference ? PsiKeyword.SUPER : "???";
            }
        });
        VARIABLE_NAME = DiagnosticParameterRendererKt.Renderer(new Function1<FirVariableSymbol<?>, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$VARIABLE_NAME$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(FirVariableSymbol<?> symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                String strAsString = symbol.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return strAsString;
            }
        });
        DECLARATION_NAME = DiagnosticParameterRendererKt.Renderer(new Function1<FirBasedSymbol<?>, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$DECLARATION_NAME$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(FirBasedSymbol<?> symbol) {
                Name shortClassName;
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                if (symbol instanceof FirCallableSymbol) {
                    shortClassName = ((FirCallableSymbol) symbol).getName();
                } else {
                    if (!(symbol instanceof FirClassLikeSymbol)) {
                        return "???";
                    }
                    shortClassName = ((FirClassLikeSymbol) symbol).getClassId().getShortClassName();
                    Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
                }
                String strAsString = shortClassName.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return strAsString;
            }
        });
        RENDER_CLASS_OR_OBJECT = DiagnosticParameterRendererKt.Renderer(new Function1<FirClassSymbol<?>, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$RENDER_CLASS_OR_OBJECT$1

            /* JADX INFO: compiled from: FirDiagnosticRenderers.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[ClassKind.values().length];
                    try {
                        iArr[ClassKind.OBJECT.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[ClassKind.INTERFACE.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // kotlin.jvm.functions.Function1
            public final String invoke(FirClassSymbol<?> classSymbol) {
                String str;
                Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
                String strAsString = classSymbol.getClassId().getRelativeClassName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                int i = WhenMappings.$EnumSwitchMapping$0[classSymbol.getClassKind().ordinal()];
                if (i == 1) {
                    str = CommonClassNames.JAVA_LANG_OBJECT_SHORT;
                } else if (i == 2) {
                    str = "Interface";
                } else {
                    str = "Class";
                }
                return str + StringUtil.SPACE + strAsString;
            }
        });
        RENDER_CLASS_OR_OBJECT_NAME = DiagnosticParameterRendererKt.Renderer(new Function1<FirClassLikeSymbol<?>, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$RENDER_CLASS_OR_OBJECT_NAME$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(FirClassLikeSymbol<?> firClassLike) {
                AssertionError assertionError;
                String str;
                Intrinsics.checkNotNullParameter(firClassLike, "firClassLike");
                String strAsString = firClassLike.getClassId().getRelativeClassName().shortName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                if (!(firClassLike instanceof FirTypeAliasSymbol)) {
                    if (!(firClassLike instanceof FirRegularClassSymbol)) {
                        assertionError = new AssertionError("Unexpected class: " + firClassLike);
                    } else {
                        if (firClassLike.getRawStatus().isCompanion()) {
                            str = "companion object";
                        } else {
                            FirClassSymbol firClassSymbol = (FirClassSymbol) firClassLike;
                            if (firClassSymbol.getClassKind() == ClassKind.INTERFACE) {
                                str = PsiKeyword.INTERFACE;
                            } else if (firClassSymbol.getClassKind() == ClassKind.ENUM_CLASS) {
                                str = "enum class";
                            } else if (firClassLike.getRawStatus().isFromEnumClass()) {
                                str = "enum entry";
                            } else if (firClassSymbol.getClassId().isLocal() || (firClassSymbol instanceof FirAnonymousObjectSymbol)) {
                                str = "object";
                            } else {
                                str = "class";
                            }
                        }
                        assertionError = str;
                    }
                }
                return assertionError + " '" + strAsString + '\'';
            }
        });
        ContextIndependentParameterRenderer<ConeKotlinType> contextIndependentParameterRendererRenderer2 = DiagnosticParameterRendererKt.Renderer(new Function1<ConeKotlinType, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$RENDER_TYPE$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(ConeKotlinType t) {
                Intrinsics.checkNotNullParameter(t, "t");
                return ConeTypeUtilsKt.renderReadableWithFqNames(t);
            }
        });
        RENDER_TYPE = contextIndependentParameterRendererRenderer2;
        RENDER_TYPE_WITH_ANNOTATIONS = contextIndependentParameterRendererRenderer2;
        FQ_NAMES_IN_TYPES = DiagnosticParameterRendererKt.Renderer(new Function1<FirBasedSymbol<?>, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$FQ_NAMES_IN_TYPES$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(FirBasedSymbol<?> symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                FirDiagnosticRenderers$FQ_NAMES_IN_TYPES$1$idRendererCreator$1 firDiagnosticRenderers$FQ_NAMES_IN_TYPES$1$idRendererCreator$1 = new Function0<ConeIdFullRenderer>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$FQ_NAMES_IN_TYPES$1$idRendererCreator$1
                    @Override // kotlin.jvm.functions.Function0
                    public final ConeIdFullRenderer invoke() {
                        return new ConeIdFullRenderer();
                    }
                };
                return new FirRenderer(null, null, null, null, null, null, null, firDiagnosticRenderers$FQ_NAMES_IN_TYPES$1$idRendererCreator$1.invoke(), null, null, null, null, new ConeTypeRendererWithJavaFlexibleTypes(firDiagnosticRenderers$FQ_NAMES_IN_TYPES$1$idRendererCreator$1), null, null, null, null, 126841, null).renderElementAsString(symbol.getFir(), true);
            }
        });
        AMBIGUOUS_CALLS = DiagnosticParameterRendererKt.Renderer(new Function1<Collection<? extends FirBasedSymbol<?>>, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$AMBIGUOUS_CALLS$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(Collection<? extends FirBasedSymbol<?>> candidates) {
                Intrinsics.checkNotNullParameter(candidates, "candidates");
                return CollectionsKt.joinToString$default(candidates, "\n", "\n", null, 0, null, new Function1<FirBasedSymbol<?>, CharSequence>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$AMBIGUOUS_CALLS$1.1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(FirBasedSymbol<?> symbol) {
                        Intrinsics.checkNotNullParameter(symbol, "symbol");
                        return FirDiagnosticRenderers.INSTANCE.getSYMBOL().render(symbol);
                    }
                }, 28, null);
            }
        });
        WHEN_MISSING_CASES = DiagnosticParameterRendererKt.Renderer(new Function1<List<? extends WhenMissingCase>, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$WHEN_MISSING_CASES$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(List<? extends WhenMissingCase> missingCases) {
                Intrinsics.checkNotNullParameter(missingCases, "missingCases");
                if (Intrinsics.areEqual(CollectionsKt.singleOrNull((List) missingCases), WhenMissingCase.Unknown.INSTANCE)) {
                    return "'else' branch";
                }
                return CollectionsKt.joinToString$default(missingCases, ", ", null, null, 7, null, new Function1<WhenMissingCase, CharSequence>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$WHEN_MISSING_CASES$1$list$1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(WhenMissingCase it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return FormatHelper.QUOTE + it + '\'';
                    }
                }, 22, null) + StringUtil.SPACE + (missingCases.size() > 1 ? "branches" : ConfigConstants.CONFIG_BRANCH_SECTION) + " or 'else' branch instead";
            }
        });
        MODULE_DATA = DiagnosticParameterRendererKt.Renderer(new Function1<FirModuleData, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$MODULE_DATA$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(FirModuleData it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return "Module " + it.getName();
            }
        });
        NAME_OF_CONTAINING_DECLARATION_OR_FILE = DiagnosticParameterRendererKt.Renderer(new Function1<CallableId, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$NAME_OF_CONTAINING_DECLARATION_OR_FILE$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(CallableId symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                return FirDiagnosticRenderers.INSTANCE.getNAME_OF_DECLARATION_OR_FILE().render(symbol.getClassId());
            }
        });
        NAME_OF_DECLARATION_OR_FILE = DiagnosticParameterRendererKt.Renderer(new Function1<ClassId, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$NAME_OF_DECLARATION_OR_FILE$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(ClassId classId) {
                if (classId == null) {
                    return "file";
                }
                return FormatHelper.QUOTE + classId + '\'';
            }
        });
        ContextIndependentParameterRenderer<FunctionTypeKind> contextIndependentParameterRendererRenderer3 = DiagnosticParameterRendererKt.Renderer(new Function1<FunctionTypeKind, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$FUNCTIONAL_TYPE_KIND$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(FunctionTypeKind kind) {
                Intrinsics.checkNotNullParameter(kind, "kind");
                String prefixForTypeRender = kind.getPrefixForTypeRender();
                return prefixForTypeRender == null ? kind.getClassNamePrefix() : prefixForTypeRender;
            }
        });
        FUNCTIONAL_TYPE_KIND = contextIndependentParameterRendererRenderer3;
        FUNCTIONAL_TYPE_KINDS = KtDiagnosticRenderers.INSTANCE.COLLECTION(contextIndependentParameterRendererRenderer3);
        REQUIRE_KOTLIN_VERSION = DiagnosticParameterRendererKt.Renderer(new Function1<VersionRequirement.Version, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$REQUIRE_KOTLIN_VERSION$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(VersionRequirement.Version version) {
                Intrinsics.checkNotNullParameter(version, "version");
                return Intrinsics.areEqual(version, VersionRequirement.Version.INFINITY) ? "" : " is only available since Kotlin " + version.asString() + " and";
            }
        });
        OPTIONAL_SENTENCE = DiagnosticParameterRendererKt.Renderer(new Function1<String, String>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers$OPTIONAL_SENTENCE$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(String str) {
                String str2 = str;
                return (str2 == null || StringsKt.isBlank(str2)) ? "" : " " + str + '.';
            }
        });
    }

    public final ContextIndependentParameterRenderer<FirBasedSymbol<?>> getSYMBOL() {
        return SYMBOL;
    }

    public final ContextIndependentParameterRenderer<Collection<? extends FirBasedSymbol<?>>> getSYMBOLS() {
        return SYMBOLS;
    }

    public final ContextIndependentParameterRenderer<Collection<? extends FirCallableSymbol<?>>> getSYMBOLS_ON_NEWLINE_WITH_INDENT() {
        return SYMBOLS_ON_NEWLINE_WITH_INDENT;
    }

    public final ContextIndependentParameterRenderer<Collection<? extends ConeKotlinType>> getRENDER_COLLECTION_OF_TYPES() {
        return RENDER_COLLECTION_OF_TYPES;
    }

    public final ContextIndependentParameterRenderer<FirExpression> getCALLEE_NAME() {
        return CALLEE_NAME;
    }

    public final ContextIndependentParameterRenderer<FirVariableSymbol<?>> getVARIABLE_NAME() {
        return VARIABLE_NAME;
    }

    public final ContextIndependentParameterRenderer<FirBasedSymbol<?>> getDECLARATION_NAME() {
        return DECLARATION_NAME;
    }

    public final ContextIndependentParameterRenderer<FirClassSymbol<?>> getRENDER_CLASS_OR_OBJECT() {
        return RENDER_CLASS_OR_OBJECT;
    }

    public final ContextIndependentParameterRenderer<FirClassLikeSymbol<?>> getRENDER_CLASS_OR_OBJECT_NAME() {
        return RENDER_CLASS_OR_OBJECT_NAME;
    }

    public final ContextIndependentParameterRenderer<ConeKotlinType> getRENDER_TYPE() {
        return RENDER_TYPE;
    }

    public final ContextIndependentParameterRenderer<ConeKotlinType> getRENDER_TYPE_WITH_ANNOTATIONS() {
        return RENDER_TYPE_WITH_ANNOTATIONS;
    }

    public final ContextIndependentParameterRenderer<FirBasedSymbol<?>> getFQ_NAMES_IN_TYPES() {
        return FQ_NAMES_IN_TYPES;
    }

    public final ContextIndependentParameterRenderer<Collection<? extends FirBasedSymbol<?>>> getAMBIGUOUS_CALLS() {
        return AMBIGUOUS_CALLS;
    }

    public final ContextIndependentParameterRenderer<List<? extends WhenMissingCase>> getWHEN_MISSING_CASES() {
        return WHEN_MISSING_CASES;
    }

    public final ContextIndependentParameterRenderer<FirModuleData> getMODULE_DATA() {
        return MODULE_DATA;
    }

    public final ContextIndependentParameterRenderer<CallableId> getNAME_OF_CONTAINING_DECLARATION_OR_FILE() {
        return NAME_OF_CONTAINING_DECLARATION_OR_FILE;
    }

    public final ContextIndependentParameterRenderer<ClassId> getNAME_OF_DECLARATION_OR_FILE() {
        return NAME_OF_DECLARATION_OR_FILE;
    }

    public final ContextIndependentParameterRenderer<FunctionTypeKind> getFUNCTIONAL_TYPE_KIND() {
        return FUNCTIONAL_TYPE_KIND;
    }

    public final ContextIndependentParameterRenderer<Collection<? extends FunctionTypeKind>> getFUNCTIONAL_TYPE_KINDS() {
        return FUNCTIONAL_TYPE_KINDS;
    }

    public final ContextIndependentParameterRenderer<VersionRequirement.Version> getREQUIRE_KOTLIN_VERSION() {
        return REQUIRE_KOTLIN_VERSION;
    }

    public final ContextIndependentParameterRenderer<String> getOPTIONAL_SENTENCE() {
        return OPTIONAL_SENTENCE;
    }
}
