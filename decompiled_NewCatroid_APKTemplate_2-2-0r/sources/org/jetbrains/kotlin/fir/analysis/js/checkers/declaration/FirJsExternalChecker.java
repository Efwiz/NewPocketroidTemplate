package org.jetbrains.kotlin.fir.analysis.js.checkers.declaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsHelpersKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyBackingField;
import org.jetbrains.kotlin.fir.declarations.impl.FirPrimaryConstructor;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.impl.FirSingleExpressionBlock;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributesKt;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JsStandardClassIds;
import org.jetbrains.kotlin.psi.KtParameter;

/* JADX INFO: compiled from: FirJsExternalChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u001c\u0010\u0016\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u001c\u0010\u0017\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u001c\u0010\u0018\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u001c\u0010\u0019\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u001c\u0010\u001a\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\f\u0010\u001b\u001a\u00020\u0006*\u00020\u0002H\u0002J\f\u0010\u001c\u001a\u00020\u0006*\u00020\u001dH\u0002J\u0014\u0010\u001e\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0014\u0010!\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u001f\u001a\u00020 H\u0002J\f\u0010\"\u001a\u00020\u0006*\u00020#H\u0002J\u0014\u0010$\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u001f\u001a\u00020 H\u0002J\u001a\u0010%\u001a\b\u0012\u0004\u0012\u00020'0&*\u00020(2\u0006\u0010\u001f\u001a\u00020 H\u0002R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b*\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/declaration/FirJsExternalChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "allowsReporting", "", "Lorg/jetbrains/kotlin/KtSourceElement;", "getAllowsReporting", "(Lorg/jetbrains/kotlin/KtSourceElement;)Z", "varargElementType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "getVarargElementType", "(Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkAnonymousInitializer", "checkBody", "checkConstructorPropertyParam", "checkDelegation", "checkEnumEntry", "hasValidExternalBody", "isDefinedExternallyExpression", "Lorg/jetbrains/kotlin/fir/FirElement;", "isDirectlyExternal", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "isNonAbstractMemberIfInterface", "isNullableProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "isPrivateMemberOfExternalClass", "superInterfaces", "", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsExternalChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirJsExternalChecker INSTANCE = new FirJsExternalChecker();

    private FirJsExternalChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, final CheckerContext context, DiagnosticReporter reporter) {
        KtDiagnosticFactory0 inline_class_in_external_declaration;
        String str;
        String str2;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (FirJsHelpersKt.isNativeObject(declaration.getSymbol(), context)) {
            if (!FirHelpersKt.isTopLevel(context) && !(declaration instanceof FirPropertyAccessor) && isDirectlyExternal(declaration, context.getSession())) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getNESTED_EXTERNAL_DECLARATION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            boolean z = declaration instanceof FirClass;
            if (z) {
                FirClass firClass = (FirClass) declaration;
                if (firClass.getStatus().isData()) {
                    str2 = "data class";
                } else if (firClass.getStatus().isInner()) {
                    str2 = "inner class";
                } else if (firClass.getStatus().isInline()) {
                    str2 = "value class";
                } else if (firClass.getStatus().isFun()) {
                    str2 = "fun interface";
                } else {
                    str2 = firClass.getClassKind() == ClassKind.ANNOTATION_CLASS ? "annotation class" : null;
                }
                String str3 = str2;
                if (str3 != null) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getWRONG_EXTERNAL_DECLARATION(), str3, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
                if (firClass.getClassKind() == ClassKind.ENUM_CLASS) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getENUM_CLASS_IN_EXTERNAL_DECLARATION_WARNING(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
            boolean z2 = declaration instanceof FirPropertyAccessor;
            if (z2 && isDirectlyExternal(declaration, context.getSession())) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getWRONG_EXTERNAL_DECLARATION(), "property accessor", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            } else if (!(declaration instanceof FirPrimaryConstructor) && !(declaration instanceof FirField) && isPrivateMemberOfExternalClass(declaration, context.getSession())) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getWRONG_EXTERNAL_DECLARATION(), "private member of class", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
            FirDeclaration firDeclaration = (FirDeclaration) CollectionsKt.lastOrNull((List) context.getContainingDeclarations());
            if (z && ((FirClass) declaration).getClassKind() != ClassKind.INTERFACE && (firDeclaration instanceof FirClass) && ((FirClass) firDeclaration).getClassKind() == ClassKind.INTERFACE) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getNESTED_CLASS_IN_EXTERNAL_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            if (!z2 && (declaration instanceof FirCallableDeclaration)) {
                FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) declaration;
                if (FirDeclarationUtilKt.isExtension(firCallableDeclaration)) {
                    if (firCallableDeclaration instanceof FirFunction) {
                        str = "extension function";
                    } else {
                        str = firCallableDeclaration instanceof FirProperty ? "extension property" : "extension member";
                    }
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getWRONG_EXTERNAL_DECLARATION(), str, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
            }
            if (z) {
                FirClass firClass2 = (FirClass) declaration;
                if (firClass2.getClassKind() != ClassKind.ANNOTATION_CLASS) {
                    List mutableList = CollectionsKt.toMutableList((Collection) superInterfaces(firClass2, context.getSession()));
                    ConeClassLikeType coneClassLikeTypeSuperClassNotAny = FirJsHelpersKt.superClassNotAny(firClass2, context.getSession());
                    if (coneClassLikeTypeSuperClassNotAny != null) {
                        mutableList.add(coneClassLikeTypeSuperClassNotAny);
                    }
                    if (firClass2.getClassKind() == ClassKind.ENUM_CLASS || firClass2.getClassKind() == ClassKind.ENUM_ENTRY) {
                        CollectionsKt.removeAll(mutableList, (Function1) new Function1<ConeClassLikeType, Boolean>() { // from class: org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsExternalChecker.check.2
                            @Override // kotlin.jvm.functions.Function1
                            public final Boolean invoke(ConeClassLikeType it) {
                                FqName fqNameAsSingleFqName;
                                Intrinsics.checkNotNullParameter(it, "it");
                                ClassId classId = ConeTypeUtilsKt.getClassId(it);
                                return Boolean.valueOf(Intrinsics.areEqual((classId == null || (fqNameAsSingleFqName = classId.asSingleFqName()) == null) ? null : fqNameAsSingleFqName.toUnsafe(), StandardNames.FqNames._enum));
                            }
                        });
                    }
                    ArrayList arrayList = new ArrayList();
                    Iterator it = mutableList.iterator();
                    while (it.hasNext()) {
                        FirClassLikeSymbol<?> symbol = TypeUtilsKt.toSymbol((ConeClassLikeType) it.next(), context.getSession());
                        if (symbol != null) {
                            arrayList.add(symbol);
                        }
                    }
                    ArrayList<FirClassLikeSymbol> arrayList2 = arrayList;
                    if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
                        for (FirClassLikeSymbol firClassLikeSymbol : arrayList2) {
                            if (!FirJsHelpersKt.isNativeObject(firClassLikeSymbol, context) && !Intrinsics.areEqual(firClassLikeSymbol.getClassId().asSingleFqName(), StandardNames.FqNames.throwable)) {
                                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getEXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                                break;
                            }
                        }
                    }
                }
            }
            if ((declaration instanceof FirFunction) && ((FirMemberDeclaration) declaration).getStatus().isInline()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getINLINE_EXTERNAL_DECLARATION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.JsAllowValueClassesInExternals)) {
                inline_class_in_external_declaration = FirJsErrors.INSTANCE.getINLINE_CLASS_IN_EXTERNAL_DECLARATION_WARNING();
            } else {
                inline_class_in_external_declaration = FirJsErrors.INSTANCE.getINLINE_CLASS_IN_EXTERNAL_DECLARATION();
            }
            check$reportOnParametersAndReturnTypesIf(declaration, reporter, context, inline_class_in_external_declaration, new Function1<ConeKotlinType, Boolean>() { // from class: org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsExternalChecker.check.4
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(ConeKotlinType it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return Boolean.valueOf(FirHelpersKt.isValueClass(it2, context.getSession()));
                }
            });
            if (!context.get$languageVersionSettings().supportsFeature(LanguageFeature.JsEnableExtensionFunctionInExternals)) {
                check$reportOnParametersAndReturnTypesIf(declaration, reporter, context, FirJsErrors.INSTANCE.getEXTENSION_FUNCTION_IN_EXTERNAL_DECLARATION(), new PropertyReference1() { // from class: org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsExternalChecker.check.5
                    @Override // kotlin.jvm.internal.CallableReference
                    public String getSignature() {
                        return "isExtensionFunctionType(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)Z";
                    }

                    @Override // kotlin.reflect.KProperty1
                    public Object get(Object obj) {
                        return Boolean.valueOf(CompilerConeAttributesKt.isExtensionFunctionType((ConeKotlinType) obj));
                    }

                    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                    public String getName() {
                        return "isExtensionFunctionType";
                    }

                    @Override // kotlin.jvm.internal.CallableReference
                    public KDeclarationContainer getOwner() {
                        return Reflection.getOrCreateKotlinPackage(CompilerConeAttributesKt.class, "checkers.js");
                    }
                });
            }
            if ((declaration instanceof FirCallableDeclaration) && isNonAbstractMemberIfInterface(declaration, context.getSession()) && !isNullableProperty((FirCallableDeclaration) declaration)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getNON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            checkBody(declaration, context, reporter);
            checkDelegation(declaration, context, reporter);
            checkAnonymousInitializer(declaration, context, reporter);
            checkEnumEntry(declaration, context, reporter);
            checkConstructorPropertyParam(declaration, context, reporter);
        }
    }

    private static final void check$reportOnParametersAndReturnTypesIf(FirDeclaration firDeclaration, DiagnosticReporter diagnosticReporter, CheckerContext checkerContext, KtDiagnosticFactory0 ktDiagnosticFactory0, Function1<? super ConeKotlinType, Boolean> function1) {
        KtSourceElement source;
        KtSourceElement source2;
        if (!(firDeclaration instanceof FirCallableDeclaration) || (firDeclaration instanceof FirDefaultPropertyAccessor) || (firDeclaration instanceof FirDefaultPropertyBackingField)) {
            return;
        }
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firDeclaration;
        KtSourceElement source3 = firCallableDeclaration.getReturnTypeRef().getSource();
        if (source3 != null && INSTANCE.getAllowsReporting(source3) && (source2 = firCallableDeclaration.getReturnTypeRef().getSource()) != null) {
            check$reportOnParametersAndReturnTypesIf$checkTypeIsNotInlineClass(function1, diagnosticReporter, ktDiagnosticFactory0, checkerContext, FirTypeUtilsKt.getConeType(firCallableDeclaration.getReturnTypeRef()), source2);
        }
        if (firDeclaration instanceof FirFunction) {
            for (FirValueParameter firValueParameter : ((FirFunction) firDeclaration).getValueParameters()) {
                KtSourceElement source4 = firValueParameter.getSource();
                if ((source4 != null ? KtSourceElementKt.getPsi(source4) : null) instanceof KtParameter) {
                    source = firValueParameter.getSource();
                } else {
                    source = firDeclaration.getSource();
                }
                KtSourceElement ktSourceElement = source;
                if (ktSourceElement != null) {
                    FirJsExternalChecker firJsExternalChecker = INSTANCE;
                    if (firJsExternalChecker.getAllowsReporting(ktSourceElement)) {
                        ConeKotlinType varargElementType = firJsExternalChecker.getVarargElementType(firValueParameter);
                        check$reportOnParametersAndReturnTypesIf$checkTypeIsNotInlineClass(function1, diagnosticReporter, ktDiagnosticFactory0, checkerContext, varargElementType == null ? FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef()) : varargElementType, ktSourceElement);
                    }
                }
            }
        }
    }

    private static final void check$reportOnParametersAndReturnTypesIf$checkTypeIsNotInlineClass(Function1<? super ConeKotlinType, Boolean> function1, DiagnosticReporter diagnosticReporter, KtDiagnosticFactory0 ktDiagnosticFactory0, CheckerContext checkerContext, ConeKotlinType coneKotlinType, KtSourceElement ktSourceElement) {
        if (function1.invoke(coneKotlinType).booleanValue()) {
            KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, ktSourceElement, ktDiagnosticFactory0, checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final boolean getAllowsReporting(KtSourceElement ktSourceElement) {
        return !(ktSourceElement.getKind() instanceof KtFakeSourceElementKind) || Intrinsics.areEqual(ktSourceElement.getKind(), KtFakeSourceElementKind.PropertyFromParameter.INSTANCE);
    }

    private final ConeKotlinType getVarargElementType(FirValueParameter firValueParameter) {
        ConeTypeProjection coneTypeProjection;
        if (firValueParameter.getIsVararg() && (coneTypeProjection = (ConeTypeProjection) ArraysKt.firstOrNull(FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef()).getTypeArguments())) != null) {
            return ConeTypeProjectionKt.getType(coneTypeProjection);
        }
        return null;
    }

    private final List<ConeClassLikeType> superInterfaces(FirClass firClass, FirSession firSession) {
        List<ConeClassLikeType> superConeTypes = FirDeclarationUtilKt.getSuperConeTypes(firClass);
        ArrayList arrayList = new ArrayList();
        for (Object obj : superConeTypes) {
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) obj;
            if (!ConeBuiltinTypeUtilsKt.isAny(coneClassLikeType) && !ConeBuiltinTypeUtilsKt.isNullableAny(coneClassLikeType)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            FirClassLikeSymbol<?> symbol = TypeUtilsKt.toSymbol((ConeClassLikeType) obj2, firSession);
            if ((symbol != null ? FirHelpersKt.getClassKind(symbol) : null) == ClassKind.INTERFACE) {
                arrayList2.add(obj2);
            }
        }
        return arrayList2;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0023  */
    private final void checkBody(FirDeclaration firDeclaration, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter) {
        FirBlock body;
        FirExpression result;
        if (firDeclaration instanceof FirDefaultPropertyAccessor) {
            return;
        }
        boolean z = firDeclaration instanceof FirFunction;
        if (z) {
            body = ((FirFunction) firDeclaration).getBody();
        } else {
            body = firDeclaration instanceof FirAnonymousInitializer ? ((FirAnonymousInitializer) firDeclaration).getBody() : null;
        }
        if (firDeclaration instanceof FirEnumEntry) {
            result = null;
        } else {
            KtSourceElement source = firDeclaration.getSource();
            if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.PropertyFromParameter.INSTANCE)) {
                result = null;
            } else if (firDeclaration instanceof FirVariable) {
                result = ((FirVariable) firDeclaration).getInitializer();
            } else if (body instanceof FirSingleExpressionBlock) {
                FirStatement statement = ((FirSingleExpressionBlock) body).getStatement();
                FirReturnExpression firReturnExpression = statement instanceof FirReturnExpression ? (FirReturnExpression) statement : null;
                if (firReturnExpression != null) {
                    result = firReturnExpression.getResult();
                } else {
                    result = null;
                }
            } else {
                result = null;
            }
        }
        KtSourceElement source2 = firDeclaration.getSource();
        if ((source2 != null ? source2.getKind() : null) instanceof KtRealSourceElementKind) {
            boolean z2 = (((body instanceof FirSingleExpressionBlock) || hasValidExternalBody(firDeclaration)) && (result == null || isDefinedExternallyExpression(result))) ? false : true;
            if (z2 && body != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, body.getSource(), FirJsErrors.INSTANCE.getWRONG_BODY_OF_EXTERNAL_DECLARATION(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            } else if (z2 && result != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, result.getSource(), FirJsErrors.INSTANCE.getWRONG_INITIALIZER_OF_EXTERNAL_DECLARATION(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            if (z) {
                List<FirValueParameter> valueParameters = ((FirFunction) firDeclaration).getValueParameters();
                ArrayList<FirExpression> arrayList = new ArrayList();
                Iterator<T> it = valueParameters.iterator();
                while (it.hasNext()) {
                    FirExpression defaultValue = ((FirValueParameter) it.next()).getDefaultValue();
                    if (defaultValue != null) {
                        arrayList.add(defaultValue);
                    }
                }
                for (FirExpression firExpression : arrayList) {
                    if (!isDefinedExternallyExpression(firExpression)) {
                        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firExpression.getSource(), FirJsErrors.INSTANCE.getWRONG_DEFAULT_VALUE_FOR_EXTERNAL_FUN_PARAMETER(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    }
                }
            }
        }
    }

    private final void checkDelegation(FirDeclaration firDeclaration, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter) {
        FirExpression delegate;
        KtSourceElement source;
        Object next;
        KtSourceElement source2;
        if ((firDeclaration instanceof FirMemberDeclaration) && FirJsHelpersKt.isEffectivelyExternal(firDeclaration.getSymbol(), checkerContext)) {
            KtSourceElementKind kind = null;
            if (firDeclaration instanceof FirClass) {
                FirClass firClass = (FirClass) firDeclaration;
                Iterator<T> it = firClass.getDeclarations().iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!(next instanceof FirPrimaryConstructor));
                FirPrimaryConstructor firPrimaryConstructor = (FirPrimaryConstructor) next;
                if (firPrimaryConstructor != null) {
                    FirDelegatedConstructorCall delegatedConstructor = firPrimaryConstructor.getDelegatedConstructor();
                    if (delegatedConstructor != null && (source2 = delegatedConstructor.getSource()) != null) {
                        kind = source2.getKind();
                    }
                    if (kind instanceof KtRealSourceElementKind) {
                        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, delegatedConstructor.getSource(), FirJsErrors.INSTANCE.getEXTERNAL_DELEGATED_CONSTRUCTOR_CALL(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    }
                }
                for (Map.Entry<FirTypeRef, FirFieldSymbol> entry : FirHelpersKt.collectSupertypesWithDelegates(firClass).entrySet()) {
                    FirTypeRef key = entry.getKey();
                    if (entry.getValue() != null) {
                        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, key.getSource(), FirJsErrors.INSTANCE.getEXTERNAL_DELEGATION(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    }
                }
                return;
            }
            if (firDeclaration instanceof FirConstructor) {
                FirConstructor firConstructor = (FirConstructor) firDeclaration;
                if (!firConstructor.getIsPrimary()) {
                    FirDelegatedConstructorCall delegatedConstructor2 = firConstructor.getDelegatedConstructor();
                    if (delegatedConstructor2 != null && (source = delegatedConstructor2.getSource()) != null) {
                        kind = source.getKind();
                    }
                    if (kind instanceof KtRealSourceElementKind) {
                        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, delegatedConstructor2.getSource(), FirJsErrors.INSTANCE.getEXTERNAL_DELEGATED_CONSTRUCTOR_CALL(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        return;
                    }
                    return;
                }
            }
            if (!(firDeclaration instanceof FirProperty) || (delegate = ((FirProperty) firDeclaration).getDelegate()) == null) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, delegate.getSource(), FirJsErrors.INSTANCE.getEXTERNAL_DELEGATION(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final void checkAnonymousInitializer(FirDeclaration firDeclaration, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter) {
        if (firDeclaration instanceof FirClass) {
            Iterator<FirAnonymousInitializer> it = FirDeclarationUtilKt.getAnonymousInitializers((FirClass) firDeclaration).iterator();
            while (it.hasNext()) {
                KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, it.next().getSource(), FirJsErrors.INSTANCE.getEXTERNAL_ANONYMOUS_INITIALIZER(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final void checkEnumEntry(FirDeclaration firDeclaration, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter) {
        FirExpression initializer;
        if ((firDeclaration instanceof FirEnumEntry) && (initializer = ((FirEnumEntry) firDeclaration).getInitializer()) != null) {
            KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, initializer.getSource(), FirJsErrors.INSTANCE.getEXTERNAL_ENUM_ENTRY_WITH_BODY(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final void checkConstructorPropertyParam(FirDeclaration firDeclaration, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter) {
        if (firDeclaration instanceof FirProperty) {
            KtSourceElement source = firDeclaration.getSource();
            if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.PropertyFromParameter.INSTANCE)) {
                FirClassLikeSymbol<?> containingClassSymbol = FirHelpersKt.getContainingClassSymbol(firDeclaration, checkerContext.getSession());
                FirClassSymbol firClassSymbol = containingClassSymbol instanceof FirClassSymbol ? (FirClassSymbol) containingClassSymbol : null;
                if (firClassSymbol == null || firClassSymbol.getRawStatus().isData() || firClassSymbol.getClassKind() == ClassKind.ANNOTATION_CLASS) {
                    return;
                }
                KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firDeclaration.getSource(), FirJsErrors.INSTANCE.getEXTERNAL_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER(), checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final boolean isDirectlyExternal(FirDeclaration firDeclaration, FirSession firSession) {
        if (firDeclaration instanceof FirDefaultPropertyAccessor) {
            return false;
        }
        KtSourceElement source = firDeclaration.getSource();
        if (!((source != null ? source.getKind() : null) instanceof KtRealSourceElementKind)) {
            return false;
        }
        KtModifierKeywordToken EXTERNAL_KEYWORD = KtTokens.EXTERNAL_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(EXTERNAL_KEYWORD, "EXTERNAL_KEYWORD");
        return FirKeywordUtilsKt.hasModifier(firDeclaration, EXTERNAL_KEYWORD) || FirAnnotationUtilsKt.hasAnnotation(firDeclaration, JsStandardClassIds.Annotations.JsNative, firSession);
    }

    private final boolean isPrivateMemberOfExternalClass(FirDeclaration firDeclaration, FirSession firSession) {
        FirClassLikeSymbol<?> containingClassSymbol;
        if (!((firDeclaration instanceof FirPropertyAccessor) && Intrinsics.areEqual(((FirMemberDeclaration) firDeclaration).getStatus().getVisibility(), ((FirPropertyAccessor) firDeclaration).getPropertySymbol().getResolvedStatus().getVisibility())) && (firDeclaration instanceof FirMemberDeclaration) && Intrinsics.areEqual(((FirMemberDeclaration) firDeclaration).getStatus().getVisibility(), Visibilities.Private.INSTANCE) && (containingClassSymbol = FirHelpersKt.getContainingClassSymbol(firDeclaration, firSession)) != null) {
            return FirJsHelpersKt.isNativeObject(containingClassSymbol, firSession);
        }
        return false;
    }

    private final boolean isNonAbstractMemberIfInterface(FirDeclaration firDeclaration, FirSession firSession) {
        if ((firDeclaration instanceof FirCallableDeclaration) && ((FirMemberDeclaration) firDeclaration).getStatus().getModality() != Modality.ABSTRACT) {
            FirClassLikeSymbol<?> containingClassSymbol = FirHelpersKt.getContainingClassSymbol(firDeclaration, firSession);
            FirClassSymbol firClassSymbol = containingClassSymbol instanceof FirClassSymbol ? (FirClassSymbol) containingClassSymbol : null;
            if ((firClassSymbol != null ? firClassSymbol.getClassKind() : null) == ClassKind.INTERFACE && !(firDeclaration instanceof FirPropertyAccessor)) {
                return true;
            }
        }
        return false;
    }

    private final boolean isNullableProperty(FirCallableDeclaration firCallableDeclaration) {
        return (firCallableDeclaration instanceof FirProperty) && ConeTypeUtilsKt.isNullable(FirTypeUtilsKt.getConeType(firCallableDeclaration.getReturnTypeRef()));
    }

    private final boolean hasValidExternalBody(FirDeclaration firDeclaration) {
        FirBlock body;
        FirStatement firStatement;
        if (firDeclaration instanceof FirFunction) {
            body = ((FirFunction) firDeclaration).getBody();
        } else {
            if (!(firDeclaration instanceof FirAnonymousInitializer)) {
                return true;
            }
            body = ((FirAnonymousInitializer) firDeclaration).getBody();
        }
        if (body instanceof FirSingleExpressionBlock) {
            return isDefinedExternallyExpression(body);
        }
        if (body == null || (firStatement = (FirStatement) CollectionsKt.singleOrNull((List) body.getStatements())) == null) {
            return false;
        }
        return isDefinedExternallyExpression(firStatement);
    }

    private final boolean isDefinedExternallyExpression(FirElement firElement) {
        FirReference calleeReference;
        FirPropertySymbol resolvedPropertySymbol$default;
        FirPropertyAccessExpression firPropertyAccessExpression = firElement instanceof FirPropertyAccessExpression ? (FirPropertyAccessExpression) firElement : null;
        if (firPropertyAccessExpression == null || (calleeReference = firPropertyAccessExpression.getCalleeReference()) == null || (resolvedPropertySymbol$default = FirReferenceUtilsKt.toResolvedPropertySymbol$default(calleeReference, false, 1, null)) == null) {
            return false;
        }
        return JsStandardClassIds.Callables.definedExternallyPropertyNames.contains(resolvedPropertySymbol$default.getCallableId());
    }
}
