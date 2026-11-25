package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirVisibilityChecker;
import org.jetbrains.kotlin.fir.FirVisibilityCheckerKt;
import org.jetbrains.kotlin.fir.analysis.FirSourceUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirDeprecationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.ConeDiagnosticToFirDiagnosticKt;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirErrorImport;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolvedImport;
import org.jetbrains.kotlin.fir.declarations.ImplicitReceiverUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirDeclaredMemberScopeProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousObjectSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirImportsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003:\u0001*B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u000f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J&\u0010\u0010\u001a\u00020\u00062\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00122\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u0013\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u0014\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00152\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002JA\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00122\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u001b2\u0018\b\u0004\u0010\u001c\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001e\u0012\u0004\u0012\u00020\u001f0\u001dH\u0082\bJ\u001c\u0010 \u001a\u00020\u0017*\u00020!2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J0\u0010\"\u001a\u00020\u001f*\u00020!2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u001b2\u0012\u0010#\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\u001f0\u001dH\u0002J\u0014\u0010%\u001a\u00020\u001f*\u00020&2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0016\u0010'\u001a\u0004\u0018\u00010!*\u00020(2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0014\u0010)\u001a\u00020\u001f*\u00020\u00152\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirImportsChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFileChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkAllUnderFromEnumEntry", PsiKeyword.IMPORT, "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "checkCanBeImported", "checkConflictingImports", "imports", "", "checkImportApiStatus", "checkOperatorRename", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedImport;", "getImportStatus", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirImportsChecker$ImportStatus;", "scopes", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "name", "Lorg/jetbrains/kotlin/name/Name;", "isApplicable", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "", "getImportStatusOfCallableMembers", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "hasFunction", "predicate", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "isVisible", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "resolveToClass", "Lorg/jetbrains/kotlin/name/ClassId;", "resolvesToClass", "ImportStatus", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirImportsChecker extends FirDeclarationChecker<FirFile> {
    public static final FirImportsChecker INSTANCE = new FirImportsChecker();

    private FirImportsChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirFile declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        for (FirImport firImport : declaration.getImports()) {
            if (!(firImport instanceof FirErrorImport)) {
                if (firImport.getIsAllUnder()) {
                    if (!(firImport instanceof FirResolvedImport)) {
                        INSTANCE.checkAllUnderFromEnumEntry(firImport, context, reporter);
                    }
                } else {
                    FirImportsChecker firImportsChecker = INSTANCE;
                    firImportsChecker.checkCanBeImported(firImport, context, reporter);
                    if (firImport instanceof FirResolvedImport) {
                        firImportsChecker.checkOperatorRename((FirResolvedImport) firImport, context, reporter);
                    }
                }
                INSTANCE.checkImportApiStatus(firImport, context, reporter);
            }
        }
        checkConflictingImports(declaration.getImports(), context, reporter);
    }

    private final void checkAllUnderFromEnumEntry(FirImport firImport, CheckerContext context, DiagnosticReporter reporter) {
        FqName importedFqName = firImport.getImportedFqName();
        if (importedFqName == null || importedFqName.isRoot() || importedFqName.parent().isRoot()) {
            return;
        }
        ClassId classId = ClassId.topLevel(importedFqName.parent());
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        FirRegularClassSymbol firRegularClassSymbolResolveToClass = resolveToClass(classId, context);
        if (firRegularClassSymbolResolveToClass == null) {
            return;
        }
        FirRegularClassSymbol firRegularClassSymbol = firRegularClassSymbolResolveToClass;
        if (firRegularClassSymbol.getClassKind() == ClassKind.ENUM_CLASS) {
            Collection<FirEnumEntrySymbol> collectionCollectEnumEntries = org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt.collectEnumEntries(firRegularClassSymbol);
            if ((collectionCollectEnumEntries instanceof Collection) && collectionCollectEnumEntries.isEmpty()) {
                return;
            }
            Iterator<T> it = collectionCollectEnumEntries.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(((FirEnumEntrySymbol) it.next()).getCallableId().getCallableName(), importedFqName.shortName())) {
                    KtSourceElement source = firImport.getSource();
                    KtDiagnosticFactory1<Name> cannot_all_under_import_from_singleton = FirErrors.INSTANCE.getCANNOT_ALL_UNDER_IMPORT_FROM_SINGLETON();
                    Name shortClassName = firRegularClassSymbolResolveToClass.getClassId().getShortClassName();
                    Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, cannot_all_under_import_from_singleton, shortClassName, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    return;
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void checkCanBeImported(FirImport firImport, CheckerContext context, DiagnosticReporter reporter) {
        FqName importedFqName = firImport.getImportedFqName();
        if (importedFqName == null) {
            return;
        }
        Name nameShortName = importedFqName.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        if (nameShortName.isSpecial()) {
            return;
        }
        String identifier = nameShortName.getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        if (identifier.length() == 0) {
            return;
        }
        FirSymbolProvider symbolProvider = FirSymbolProviderKt.getSymbolProvider(context.getSession());
        Object fir = null;
        FirResolvedImport firResolvedImport = firImport instanceof FirResolvedImport ? (FirResolvedImport) firImport : null;
        ClassId resolvedParentClassId = firResolvedImport != null ? firResolvedImport.getResolvedParentClassId() : null;
        if (resolvedParentClassId != null) {
            FirRegularClassSymbol firRegularClassSymbolResolveToClass = resolveToClass(resolvedParentClassId, context);
            if (firRegularClassSymbolResolveToClass == null) {
                return;
            }
            checkCanBeImported$reportInvisibleParentClasses(context, firImport, reporter, firRegularClassSymbolResolveToClass, 1);
            ImportStatus importStatusOfCallableMembers = getImportStatusOfCallableMembers(firRegularClassSymbolResolveToClass, context, nameShortName);
            if (Intrinsics.areEqual(importStatusOfCallableMembers, ImportStatus.OK.INSTANCE)) {
                return;
            }
            if (importStatusOfCallableMembers instanceof ImportStatus.Invisible) {
                reporter.report(ConeDiagnosticToFirDiagnosticKt.toInvisibleReferenceDiagnostic(((ImportStatus.Invisible) importStatusOfCallableMembers).getSymbol(), FirSourceUtilsKt.getSourceForImportSegment(firImport, 0)), context);
                return;
            }
            ClassId classIdCreateNestedClassId = firRegularClassSymbolResolveToClass.getClassId().createNestedClassId(nameShortName);
            Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
            if (symbolProvider.getClassLikeSymbolByClassId(classIdCreateNestedClassId) != null) {
                return;
            }
            if (Intrinsics.areEqual(importStatusOfCallableMembers, ImportStatus.Unresolved.INSTANCE)) {
                KtSourceElement source = firImport.getSource();
                KtDiagnosticFactory1<String> unresolved_import = FirErrors.INSTANCE.getUNRESOLVED_IMPORT();
                String strAsString = nameShortName.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, unresolved_import, strAsString, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firImport.getSource(), FirErrors.INSTANCE.getCANNOT_BE_IMPORTED(), nameShortName, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            return;
        }
        ClassId classId = ClassId.topLevel(importedFqName);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        FirRegularClassSymbol firRegularClassSymbolResolveToClass2 = resolveToClass(classId, context);
        if (firRegularClassSymbolResolveToClass2 != null) {
            fir = firRegularClassSymbolResolveToClass2.getFir();
            if (INSTANCE.isVisible((FirMemberDeclaration) firRegularClassSymbolResolveToClass2.getFir(), context)) {
                return;
            }
        }
        FqName fqNameParent = importedFqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        for (FirCallableSymbol<?> firCallableSymbol : symbolProvider.getTopLevelCallableSymbols(fqNameParent, nameShortName)) {
            if (isVisible((FirMemberDeclaration) firCallableSymbol.getFir(), context)) {
                return;
            }
            if (fir == null) {
                fir = firCallableSymbol.getFir();
            }
        }
        FirMemberDeclaration firMemberDeclaration = (FirMemberDeclaration) fir;
        if (firMemberDeclaration != null) {
            KtSourceElement sourceForImportSegment = FirSourceUtilsKt.getSourceForImportSegment(firImport, 0);
            if (sourceForImportSegment == null) {
                sourceForImportSegment = firImport.getSource();
            }
            reporter.report(ConeDiagnosticToFirDiagnosticKt.toInvisibleReferenceDiagnostic(firMemberDeclaration.getSymbol(), sourceForImportSegment), context);
            return;
        }
        if (symbolProvider.getPackage(importedFqName) != null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firImport.getSource(), FirErrors.INSTANCE.getPACKAGE_CANNOT_BE_IMPORTED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        KtSourceElement source2 = firImport.getSource();
        KtDiagnosticFactory1<String> unresolved_import2 = FirErrors.INSTANCE.getUNRESOLVED_IMPORT();
        String strAsString2 = nameShortName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source2, unresolved_import2, strAsString2, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final void checkCanBeImported$reportInvisibleParentClasses(CheckerContext checkerContext, FirImport firImport, DiagnosticReporter diagnosticReporter, FirRegularClassSymbol firRegularClassSymbol, int i) {
        FirRegularClassSymbol firRegularClassSymbolResolveToClass;
        FirImportsChecker firImportsChecker = INSTANCE;
        if (!firImportsChecker.isVisible((FirMemberDeclaration) firRegularClassSymbol.getFir(), checkerContext)) {
            diagnosticReporter.report(ConeDiagnosticToFirDiagnosticKt.toInvisibleReferenceDiagnostic(firRegularClassSymbol, FirSourceUtilsKt.getSourceForImportSegment(firImport, i)), checkerContext);
        }
        ClassId outerClassId = firRegularClassSymbol.getClassId().getOuterClassId();
        if (outerClassId == null || (firRegularClassSymbolResolveToClass = firImportsChecker.resolveToClass(outerClassId, checkerContext)) == null) {
            return;
        }
        checkCanBeImported$reportInvisibleParentClasses(checkerContext, firImport, diagnosticReporter, firRegularClassSymbolResolveToClass, i + 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isVisible(FirMemberDeclaration firMemberDeclaration, CheckerContext checkerContext) {
        FirFile containingFile = checkerContext.getContainingFile();
        if (containingFile == null) {
            return false;
        }
        Visibility visibility = firMemberDeclaration.getStatus().getVisibility();
        if (!Intrinsics.areEqual(visibility, Visibilities.Unknown.INSTANCE) && !visibility.mustCheckInImports()) {
            return true;
        }
        if (Intrinsics.areEqual(visibility, Visibilities.Private.INSTANCE) || Intrinsics.areEqual(visibility, Visibilities.PrivateToThis.INSTANCE)) {
            return Intrinsics.areEqual(containingFile, FirProviderUtilsKt.getContainingFile(FirProviderKt.getFirProvider(checkerContext.getSession()), firMemberDeclaration.getSymbol()));
        }
        return FirVisibilityChecker.isVisible$default(FirVisibilityCheckerKt.getVisibilityChecker(checkerContext.getSession()), firMemberDeclaration, checkerContext.getSession(), containingFile, CollectionsKt.emptyList(), null, false, null, true, null, 352, null);
    }

    private final void checkConflictingImports(List<? extends FirImport> imports, CheckerContext context, DiagnosticReporter reporter) {
        Name importedName;
        String identifierOrNullIfSpecial;
        ArrayList arrayList = new ArrayList();
        for (Object obj : imports) {
            if (obj instanceof FirResolvedImport) {
                FirResolvedImport firResolvedImport = (FirResolvedImport) obj;
                if (!firResolvedImport.getIsAllUnder() && (importedName = firResolvedImport.getImportedName()) != null && (identifierOrNullIfSpecial = importedName.getIdentifierOrNullIfSpecial()) != null) {
                    Intrinsics.checkNotNull(identifierOrNullIfSpecial);
                    if (identifierOrNullIfSpecial.length() > 0 && INSTANCE.resolvesToClass(firResolvedImport, context)) {
                        arrayList.add(obj);
                    }
                }
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj2 : arrayList) {
            FirResolvedImport firResolvedImport2 = (FirResolvedImport) obj2;
            Name aliasName = firResolvedImport2.getAliasName();
            if (aliasName == null) {
                aliasName = firResolvedImport2.getImportedName();
                Intrinsics.checkNotNull(aliasName);
            }
            Object obj3 = linkedHashMap.get(aliasName);
            if (obj3 == null) {
                obj3 = (List) new ArrayList();
                linkedHashMap.put(aliasName, obj3);
            }
            ((List) obj3).add(obj2);
        }
        Collection collectionValues = linkedHashMap.values();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj4 : collectionValues) {
            if (((List) obj4).size() > 1) {
                arrayList2.add(obj4);
            }
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            for (FirResolvedImport firResolvedImport3 : (List) it.next()) {
                KtSourceElement source = firResolvedImport3.getSource();
                KtDiagnosticFactory1<Name> conflicting_import = FirErrors.INSTANCE.getCONFLICTING_IMPORT();
                Name importedName2 = firResolvedImport3.getImportedName();
                Intrinsics.checkNotNull(importedName2);
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, conflicting_import, importedName2, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
    }

    private final void checkOperatorRename(FirResolvedImport firResolvedImport, CheckerContext context, DiagnosticReporter reporter) {
        Name importedName;
        Name aliasName = firResolvedImport.getAliasName();
        if (aliasName == null || (importedName = firResolvedImport.getImportedName()) == null || !OperatorConventions.isConventionName(aliasName)) {
            return;
        }
        ClassId resolvedParentClassId = firResolvedImport.getResolvedParentClassId();
        if (resolvedParentClassId != null) {
            FirRegularClassSymbol firRegularClassSymbolResolveToClass = resolveToClass(resolvedParentClassId, context);
            if (firRegularClassSymbolResolveToClass == null || !firRegularClassSymbolResolveToClass.getClassKind().isSingleton() || !hasFunction(firRegularClassSymbolResolveToClass, context, importedName, new Function1<FirNamedFunctionSymbol, Boolean>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirImportsChecker$checkOperatorRename$illegalRename$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(FirNamedFunctionSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(it.getResolvedStatus().isOperator());
                }
            })) {
                return;
            }
        } else {
            List<FirNamedFunctionSymbol> topLevelFunctionSymbols = FirSymbolProviderKt.getSymbolProvider(context.getSession()).getTopLevelFunctionSymbols(firResolvedImport.getPackageFqName(), importedName);
            if ((topLevelFunctionSymbols instanceof Collection) && topLevelFunctionSymbols.isEmpty()) {
                return;
            }
            Iterator<T> it = topLevelFunctionSymbols.iterator();
            while (it.hasNext()) {
                if (((FirNamedFunctionSymbol) it.next()).getResolvedStatus().isOperator()) {
                }
            }
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firResolvedImport.getSource(), FirErrors.INSTANCE.getOPERATOR_RENAMED_ON_IMPORT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }

    private final boolean resolvesToClass(FirResolvedImport firResolvedImport, CheckerContext checkerContext) {
        Name importedName;
        if (firResolvedImport.getResolvedParentClassId() != null) {
            if (firResolvedImport.getIsAllUnder()) {
                return true;
            }
            ClassId resolvedParentClassId = firResolvedImport.getResolvedParentClassId();
            Intrinsics.checkNotNull(resolvedParentClassId);
            FqName relativeParentClassName = firResolvedImport.getRelativeParentClassName();
            return (relativeParentClassName == null || (importedName = firResolvedImport.getImportedName()) == null || resolveToClass(new ClassId(resolvedParentClassId.getPackageFqName(), relativeParentClassName.child(importedName), false), checkerContext) == null) ? false : true;
        }
        FqName importedFqName = firResolvedImport.getImportedFqName();
        if (importedFqName == null || importedFqName.isRoot()) {
            return false;
        }
        ClassId classId = ClassId.topLevel(importedFqName);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        return resolveToClass(classId, checkerContext) != null;
    }

    private final FirRegularClassSymbol resolveToClass(ClassId classId, CheckerContext checkerContext) {
        FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(checkerContext.getSession()).getClassLikeSymbolByClassId(classId);
        if (classLikeSymbolByClassId == null) {
            return null;
        }
        if (classLikeSymbolByClassId instanceof FirRegularClassSymbol) {
            return (FirRegularClassSymbol) classLikeSymbolByClassId;
        }
        if (classLikeSymbolByClassId instanceof FirTypeAliasSymbol) {
            return org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt.fullyExpandedClass(classLikeSymbolByClassId, checkerContext.getSession());
        }
        if (classLikeSymbolByClassId instanceof FirAnonymousObjectSymbol) {
            return null;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final boolean hasFunction(FirRegularClassSymbol firRegularClassSymbol, CheckerContext checkerContext, Name name2, final Function1<? super FirNamedFunctionSymbol, Boolean> function1) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        FirDeclaredMemberScopeProviderKt.declaredMemberScope(checkerContext.getSession(), firRegularClassSymbol, (FirResolvePhase) null).processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirImportsChecker.hasFunction.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol sym) {
                Intrinsics.checkNotNullParameter(sym, "sym");
                if (booleanRef.element) {
                    return;
                }
                booleanRef.element = function1.invoke(sym).booleanValue();
            }
        });
        return booleanRef.element;
    }

    /* JADX INFO: compiled from: FirImportsChecker.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirImportsChecker$ImportStatus;", "", "()V", "CannotBeImported", "Invisible", "OK", "Unresolved", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirImportsChecker$ImportStatus$CannotBeImported;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirImportsChecker$ImportStatus$Invisible;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirImportsChecker$ImportStatus$OK;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirImportsChecker$ImportStatus$Unresolved;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static abstract class ImportStatus {
        public /* synthetic */ ImportStatus(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: FirImportsChecker.kt */
        @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÖ\u0003J\t\u0010\u0007\u001a\u00020\bHÖ\u0001J\t\u0010\t\u001a\u00020\nHÖ\u0001¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirImportsChecker$ImportStatus$OK;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirImportsChecker$ImportStatus;", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class OK extends ImportStatus {
            public static final OK INSTANCE = new OK();

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof OK)) {
                    return false;
                }
                return true;
            }

            public int hashCode() {
                return -1967623169;
            }

            public String toString() {
                return "OK";
            }

            private OK() {
                super(null);
            }
        }

        private ImportStatus() {
        }

        /* JADX INFO: compiled from: FirImportsChecker.kt */
        @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004J\r\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0003HÆ\u0003J\u0017\u0010\b\u001a\u00020\u00002\f\b\u0002\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirImportsChecker$ImportStatus$Invisible;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirImportsChecker$ImportStatus;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)V", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class Invisible extends ImportStatus {
            private final FirCallableSymbol<?> symbol;

            /* JADX WARN: Multi-variable type inference failed */
            public static /* synthetic */ Invisible copy$default(Invisible invisible, FirCallableSymbol firCallableSymbol, int i, Object obj) {
                if ((i & 1) != 0) {
                    firCallableSymbol = invisible.symbol;
                }
                return invisible.copy(firCallableSymbol);
            }

            public final FirCallableSymbol<?> component1() {
                return this.symbol;
            }

            public final Invisible copy(FirCallableSymbol<?> symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                return new Invisible(symbol);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof Invisible) && Intrinsics.areEqual(this.symbol, ((Invisible) other).symbol);
            }

            public int hashCode() {
                return this.symbol.hashCode();
            }

            public String toString() {
                return "Invisible(symbol=" + this.symbol + ')';
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Invisible(FirCallableSymbol<?> symbol) {
                super(null);
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                this.symbol = symbol;
            }

            public final FirCallableSymbol<?> getSymbol() {
                return this.symbol;
            }
        }

        /* JADX INFO: compiled from: FirImportsChecker.kt */
        @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÖ\u0003J\t\u0010\u0007\u001a\u00020\bHÖ\u0001J\t\u0010\t\u001a\u00020\nHÖ\u0001¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirImportsChecker$ImportStatus$CannotBeImported;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirImportsChecker$ImportStatus;", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class CannotBeImported extends ImportStatus {
            public static final CannotBeImported INSTANCE = new CannotBeImported();

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof CannotBeImported)) {
                    return false;
                }
                return true;
            }

            public int hashCode() {
                return 1558473421;
            }

            public String toString() {
                return "CannotBeImported";
            }

            private CannotBeImported() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: FirImportsChecker.kt */
        @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÖ\u0003J\t\u0010\u0007\u001a\u00020\bHÖ\u0001J\t\u0010\t\u001a\u00020\nHÖ\u0001¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirImportsChecker$ImportStatus$Unresolved;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirImportsChecker$ImportStatus;", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class Unresolved extends ImportStatus {
            public static final Unresolved INSTANCE = new Unresolved();

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof Unresolved)) {
                    return false;
                }
                return true;
            }

            public int hashCode() {
                return -1417964332;
            }

            public String toString() {
                return "Unresolved";
            }

            private Unresolved() {
                super(null);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final ImportStatus getImportStatusOfCallableMembers(FirRegularClassSymbol firRegularClassSymbol, final CheckerContext checkerContext, Name name2) {
        if (firRegularClassSymbol.getClassKind().isSingleton()) {
            FirRegularClassSymbol firRegularClassSymbol2 = firRegularClassSymbol;
            List<FirContainingNamesAwareScope> listListOf = CollectionsKt.listOf((Object[]) new FirContainingNamesAwareScope[]{FirDeclaredMemberScopeProviderKt.declaredMemberScope(checkerContext.getSession(), firRegularClassSymbol2, (FirResolvePhase) null), FirHelpersKt.unsubstitutedScope(firRegularClassSymbol2, checkerContext)});
            final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            for (FirContainingNamesAwareScope firContainingNamesAwareScope : listListOf) {
                firContainingNamesAwareScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirImportsChecker$getImportStatusOfCallableMembers$$inlined$getImportStatus$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                        invoke2(firNamedFunctionSymbol);
                        return Unit.INSTANCE;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirNamedFunctionSymbol sym) {
                        Intrinsics.checkNotNullParameter(sym, "sym");
                        if (FirImportsChecker.INSTANCE.isVisible((FirMemberDeclaration) sym.getFir(), checkerContext)) {
                            booleanRef.element = true;
                        }
                        objectRef.element = sym;
                    }
                });
                if (booleanRef.element) {
                    return ImportStatus.OK.INSTANCE;
                }
                firContainingNamesAwareScope.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirImportsChecker$getImportStatusOfCallableMembers$$inlined$getImportStatus$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                        invoke2(firVariableSymbol);
                        return Unit.INSTANCE;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirVariableSymbol<?> sym) {
                        Intrinsics.checkNotNullParameter(sym, "sym");
                        if (FirImportsChecker.INSTANCE.isVisible((FirMemberDeclaration) sym.getFir(), checkerContext)) {
                            booleanRef.element = true;
                        }
                        objectRef.element = sym;
                    }
                });
                if (booleanRef.element) {
                    return ImportStatus.OK.INSTANCE;
                }
            }
            if (((FirCallableSymbol) objectRef.element) == null) {
                return objectRef.element != 0 ? ImportStatus.CannotBeImported.INSTANCE : ImportStatus.Unresolved.INSTANCE;
            }
            T t = objectRef.element;
            Intrinsics.checkNotNull(t);
            return new ImportStatus.Invisible((FirCallableSymbol) t);
        }
        FirRegularClassSymbol firRegularClassSymbol3 = firRegularClassSymbol;
        List<FirContainingNamesAwareScope> listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) new FirContainingNamesAwareScope[]{FirDeclaredMemberScopeProviderKt.declaredMemberScope(checkerContext.getSession(), firRegularClassSymbol3, (FirResolvePhase) null), ImplicitReceiverUtilsKt.staticScope((FirClass) firRegularClassSymbol.getFir(), checkerContext.getSessionHolder()), FirHelpersKt.unsubstitutedScope(firRegularClassSymbol3, checkerContext)});
        final Ref.BooleanRef booleanRef2 = new Ref.BooleanRef();
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        for (FirContainingNamesAwareScope firContainingNamesAwareScope2 : listListOfNotNull) {
            firContainingNamesAwareScope2.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirImportsChecker$getImportStatusOfCallableMembers$$inlined$getImportStatus$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                    invoke2(firNamedFunctionSymbol);
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirNamedFunctionSymbol sym) {
                    Intrinsics.checkNotNullParameter(sym, "sym");
                    if (FirImportsChecker.INSTANCE.isVisible((FirMemberDeclaration) sym.getFir(), checkerContext) && sym.getRawStatus().isStatic()) {
                        booleanRef2.element = true;
                    }
                    objectRef2.element = sym;
                }
            });
            if (booleanRef2.element) {
                return ImportStatus.OK.INSTANCE;
            }
            firContainingNamesAwareScope2.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirImportsChecker$getImportStatusOfCallableMembers$$inlined$getImportStatus$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                    invoke2(firVariableSymbol);
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirVariableSymbol<?> sym) {
                    Intrinsics.checkNotNullParameter(sym, "sym");
                    if (FirImportsChecker.INSTANCE.isVisible((FirMemberDeclaration) sym.getFir(), checkerContext) && sym.getRawStatus().isStatic()) {
                        booleanRef2.element = true;
                    }
                    objectRef2.element = sym;
                }
            });
            if (booleanRef2.element) {
                return ImportStatus.OK.INSTANCE;
            }
        }
        FirCallableSymbol firCallableSymbol = (FirCallableSymbol) objectRef2.element;
        if (firCallableSymbol == null || !firCallableSymbol.getRawStatus().isStatic()) {
            return objectRef2.element != 0 ? ImportStatus.CannotBeImported.INSTANCE : ImportStatus.Unresolved.INSTANCE;
        }
        T t2 = objectRef2.element;
        Intrinsics.checkNotNull(t2);
        return new ImportStatus.Invisible((FirCallableSymbol) t2);
    }

    private final void checkImportApiStatus(FirImport firImport, CheckerContext context, DiagnosticReporter reporter) {
        ClassId resolvedParentClassId;
        FqName importedFqName = firImport.getImportedFqName();
        if (importedFqName == null || importedFqName.isRoot()) {
            return;
        }
        String strAsString = importedFqName.shortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (strAsString.length() == 0) {
            return;
        }
        FirResolvedImport firResolvedImport = firImport instanceof FirResolvedImport ? (FirResolvedImport) firImport : null;
        if (firResolvedImport == null || (resolvedParentClassId = firResolvedImport.getResolvedParentClassId()) == null) {
            resolvedParentClassId = ClassId.topLevel(importedFqName);
            Intrinsics.checkNotNullExpressionValue(resolvedParentClassId, "topLevel(...)");
        }
        FirClassifierSymbol<?> symbol = FirSymbolProviderKt.toSymbol(resolvedParentClassId, context.getSession());
        if (symbol == null) {
            return;
        }
        FirDeprecationChecker.reportApiStatusIfNeeded$checkers$default(FirDeprecationChecker.INSTANCE, firImport.getSource(), symbol, context, reporter, null, 16, null);
    }
}
