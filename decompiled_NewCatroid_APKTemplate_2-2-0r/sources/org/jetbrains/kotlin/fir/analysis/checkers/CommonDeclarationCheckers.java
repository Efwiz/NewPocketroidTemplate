package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.fir.analysis.cfa.AbstractFirPropertyInitializationChecker;
import org.jetbrains.kotlin.fir.analysis.cfa.FirCallsEffectAnalyzer;
import org.jetbrains.kotlin.fir.analysis.cfa.FirPropertyInitializationAnalyzer;
import org.jetbrains.kotlin.fir.analysis.checkers.cfa.FirControlFlowChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.ContractSyntaxV2FunctionChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.ContractSyntaxV2PropertyChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirActualCallableDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirActualTypeAliasChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirActualTypealiasToSpecialAnnotationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirAmbiguousAnonymousTypeChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirAnnotationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirAnnotationClassDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirAnonymousInitializerInInterfaceChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirClassVarianceChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirCommonConstructorDelegationIssuesChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirConflictsDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirConstPropertyChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirConstructorAllowedChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirContractChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirCyclicTypeBoundsChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDataClassPrimaryConstructorChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDataObjectContentChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDelegateFieldTypeMismatchChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDelegateUsesExtensionPropertyTypeParameterChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDelegatedPropertyChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDelegationSuperCallInEnumConstructorChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDestructuringDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDynamicReceiverChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDynamicSupertypeChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirEnumClassSimpleChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirEnumCompanionInEnumConstructorCallChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirExpectActualClassifiersAreInBetaChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirExpectActualDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirExpectConsistencyChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirExplicitBackingFieldForbiddenChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirExplicitBackingFieldsUnsupportedChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirExposedVisibilityDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFiniteBoundRestrictionChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFunInterfaceDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFunctionNameChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFunctionParameterChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFunctionReturnChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirImplementationMismatchChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirImplicitNothingReturnTypeChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirImportsChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirInapplicableLateinitChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirInfixFunctionDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirInitializerTypeMismatchChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirInlineDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirInlinePropertyChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirKClassWithIncorrectTypeArgumentChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirLocalEntityNotAllowedChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirLocalExtensionPropertyChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirManyCompanionObjectsChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirMemberFunctionsChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirMemberPropertiesChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirMethodOfAnyImplementedInInterfaceChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirModifierChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirNestedClassChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirNotImplementedOverrideChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirNotImplementedOverrideSimpleEnumEntryChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirOpenMemberChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirOperatorModifierChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirOptInAnnotationClassChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirOptInMarkedDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirOuterClassArgumentsRequiredChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirOverrideChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirPrimaryConstructorSuperTypeChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirProjectionRelationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirPropertyAccessorsTypesChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirPropertyFieldTypeChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirPropertyFromParameterChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirPropertyInitializationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirPropertyTypeParametersChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirPublishedApiChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirReifiedTypeParameterChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirReservedUnderscoreDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirSealedSupertypeChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirSupertypesChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirSuspendLimitationsChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirTailrecFunctionChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirThrowableSubclassChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirTopLevelFunctionsChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirTopLevelPropertiesChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirTopLevelTypeAliasChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirTypeAliasExpandsToArrayOfNothingsChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirTypeConstraintsChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirTypeParameterBoundsChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirTypeParameterVarianceChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirTypeParametersInObjectChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirUpperBoundViolatedDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirValueClassDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirValueParameterDefaultValueTypeMismatchChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirVolatileAnnotationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirAnonymousFunctionParametersChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirAnonymousFunctionSyntaxChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirDelegationInInterfaceSyntaxChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirExplicitApiDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirFunctionTypeParametersSyntaxChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirLocalVariableTypeParametersSyntaxChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirTypeParameterSyntaxChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirUnresolvedInMiddleOfImportChecker;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;

/* JADX INFO: compiled from: CommonDeclarationCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ä\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R$\u0010\u0003\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR$\u0010\n\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000b0\u0005j\u0002`\f0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\tR$\u0010\u000e\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000f0\u0005j\u0002`\u00100\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\tR$\u0010\u0012\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00130\u0005j\u0002`\u00140\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\tR$\u0010\u0016\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00170\u0005j\u0002`\u00180\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\tR$\u0010\u001a\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001b0\u0005j\u0002`\u001c0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\tR$\u0010\u001e\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001f0\u0005j\u0002` 0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\tR$\u0010\"\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020#0\u0005j\u0002`$0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\tR\u001a\u0010&\u001a\b\u0012\u0004\u0012\u00020'0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010\tR$\u0010)\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020*0\u0005j\u0002`+0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010\tR$\u0010-\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020.0\u0005j\u0002`/0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b0\u0010\tR$\u00101\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002020\u0005j\u0002`30\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b4\u0010\tR$\u00105\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002060\u0005j\u0002`70\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b8\u0010\tR$\u00109\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020:0\u0005j\u0002`;0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b<\u0010\tR$\u0010=\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020>0\u0005j\u0002`?0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b@\u0010\tR$\u0010A\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020B0\u0005j\u0002`C0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bD\u0010\tR$\u0010E\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020F0\u0005j\u0002`G0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bH\u0010\tR\u001a\u0010I\u001a\b\u0012\u0004\u0012\u00020J0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bK\u0010\t¨\u0006L"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/CommonDeclarationCheckers;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers;", "()V", "anonymousFunctionCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirAnonymousFunctionChecker;", "getAnonymousFunctionCheckers", "()Ljava/util/Set;", "anonymousInitializerCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirAnonymousInitializerChecker;", "getAnonymousInitializerCheckers", "backingFieldCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBackingFieldChecker;", "getBackingFieldCheckers", "basicDeclarationCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "getBasicDeclarationCheckers", "callableDeclarationCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirCallableDeclarationChecker;", "getCallableDeclarationCheckers", "classCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "getClassCheckers", "classLikeCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassLikeChecker;", "getClassLikeCheckers", "constructorCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirConstructorChecker;", "getConstructorCheckers", "controlFlowAnalyserCheckers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/cfa/FirControlFlowChecker;", "getControlFlowAnalyserCheckers", "fileCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFileChecker;", "getFileCheckers", "functionCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFunctionChecker;", "getFunctionCheckers", "propertyCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyChecker;", "getPropertyCheckers", "regularClassCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "getRegularClassCheckers", "simpleFunctionCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirSimpleFunctionChecker;", "getSimpleFunctionCheckers", "typeAliasCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirTypeAliasChecker;", "getTypeAliasCheckers", "typeParameterCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirTypeParameterChecker;", "getTypeParameterCheckers", "valueParameterCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirValueParameterChecker;", "getValueParameterCheckers", "variableAssignmentCfaBasedCheckers", "Lorg/jetbrains/kotlin/fir/analysis/cfa/AbstractFirPropertyInitializationChecker;", "getVariableAssignmentCfaBasedCheckers", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommonDeclarationCheckers extends DeclarationCheckers {
    public static final CommonDeclarationCheckers INSTANCE = new CommonDeclarationCheckers();

    private CommonDeclarationCheckers() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirDeclaration>> getBasicDeclarationCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirModifierChecker.INSTANCE, FirConflictsDeclarationChecker.INSTANCE, FirProjectionRelationChecker.INSTANCE, FirTypeConstraintsChecker.INSTANCE, FirReservedUnderscoreDeclarationChecker.INSTANCE, FirUpperBoundViolatedDeclarationChecker.INSTANCE, FirInfixFunctionDeclarationChecker.INSTANCE, FirExposedVisibilityDeclarationChecker.INSTANCE, FirCyclicTypeBoundsChecker.INSTANCE, FirExpectActualDeclarationChecker.INSTANCE, FirAmbiguousAnonymousTypeChecker.INSTANCE, FirExplicitApiDeclarationChecker.INSTANCE, FirAnnotationChecker.INSTANCE, FirPublishedApiChecker.INSTANCE, FirOptInMarkedDeclarationChecker.INSTANCE, FirExpectConsistencyChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirClassLikeDeclaration>> getClassLikeCheckers() {
        return SetsKt.setOf(FirExpectActualClassifiersAreInBetaChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirCallableDeclaration>> getCallableDeclarationCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirKClassWithIncorrectTypeArgumentChecker.INSTANCE, FirImplicitNothingReturnTypeChecker.INSTANCE, FirDynamicReceiverChecker.INSTANCE, FirActualCallableDeclarationChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirFunction>> getFunctionCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirContractChecker.INSTANCE, FirFunctionParameterChecker.INSTANCE, FirFunctionReturnChecker.INSTANCE, FirInlineDeclarationChecker.INSTANCE, FirSuspendLimitationsChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirSimpleFunction>> getSimpleFunctionCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirFunctionNameChecker.INSTANCE, FirFunctionTypeParametersSyntaxChecker.INSTANCE, FirOperatorModifierChecker.INSTANCE, FirTailrecFunctionChecker.INSTANCE, FirMemberFunctionsChecker.INSTANCE, FirDataObjectContentChecker.INSTANCE, ContractSyntaxV2FunctionChecker.INSTANCE, FirTopLevelFunctionsChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirProperty>> getPropertyCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirInapplicableLateinitChecker.INSTANCE, FirDestructuringDeclarationChecker.INSTANCE, FirConstPropertyChecker.INSTANCE, FirPropertyAccessorsTypesChecker.INSTANCE, FirPropertyTypeParametersChecker.INSTANCE, FirInitializerTypeMismatchChecker.INSTANCE, FirDelegatedPropertyChecker.INSTANCE, FirPropertyFieldTypeChecker.INSTANCE, FirPropertyFromParameterChecker.INSTANCE, FirLocalVariableTypeParametersSyntaxChecker.INSTANCE, FirDelegateUsesExtensionPropertyTypeParameterChecker.INSTANCE, FirTopLevelPropertiesChecker.INSTANCE, FirLocalExtensionPropertyChecker.INSTANCE, ContractSyntaxV2PropertyChecker.INSTANCE, FirVolatileAnnotationChecker.INSTANCE, FirInlinePropertyChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirBackingField>> getBackingFieldCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirExplicitBackingFieldForbiddenChecker.INSTANCE, FirExplicitBackingFieldsUnsupportedChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirClass>> getClassCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirOverrideChecker.INSTANCE, FirNotImplementedOverrideChecker.INSTANCE, FirNotImplementedOverrideSimpleEnumEntryChecker.INSTANCE, FirThrowableSubclassChecker.INSTANCE, FirOpenMemberChecker.INSTANCE, FirClassVarianceChecker.INSTANCE, FirSealedSupertypeChecker.INSTANCE, FirMemberPropertiesChecker.INSTANCE, FirImplementationMismatchChecker.INSTANCE, FirTypeParametersInObjectChecker.INSTANCE, FirSupertypesChecker.INSTANCE, FirPrimaryConstructorSuperTypeChecker.INSTANCE, FirDynamicSupertypeChecker.INSTANCE, FirEnumCompanionInEnumConstructorCallChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirRegularClass>> getRegularClassCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirAnnotationClassDeclarationChecker.INSTANCE, FirOptInAnnotationClassChecker.INSTANCE, FirCommonConstructorDelegationIssuesChecker.INSTANCE, FirDelegationSuperCallInEnumConstructorChecker.INSTANCE, FirDelegationInInterfaceSyntaxChecker.INSTANCE, FirEnumClassSimpleChecker.INSTANCE, FirLocalEntityNotAllowedChecker.INSTANCE, FirManyCompanionObjectsChecker.INSTANCE, FirMethodOfAnyImplementedInInterfaceChecker.INSTANCE, FirDataClassPrimaryConstructorChecker.INSTANCE, FirFunInterfaceDeclarationChecker.INSTANCE, FirNestedClassChecker.INSTANCE, FirValueClassDeclarationChecker.INSTANCE, FirOuterClassArgumentsRequiredChecker.INSTANCE, FirPropertyInitializationChecker.INSTANCE, FirDelegateFieldTypeMismatchChecker.INSTANCE, FirFiniteBoundRestrictionChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirConstructor>> getConstructorCheckers() {
        return SetsKt.setOf(FirConstructorAllowedChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirFile>> getFileCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirImportsChecker.INSTANCE, FirUnresolvedInMiddleOfImportChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirControlFlowChecker> getControlFlowAnalyserCheckers() {
        return SetsKt.setOf(FirCallsEffectAnalyzer.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<AbstractFirPropertyInitializationChecker> getVariableAssignmentCfaBasedCheckers() {
        return SetsKt.setOf(FirPropertyInitializationAnalyzer.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirTypeParameter>> getTypeParameterCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirTypeParameterBoundsChecker.INSTANCE, FirTypeParameterVarianceChecker.INSTANCE, FirReifiedTypeParameterChecker.INSTANCE, FirTypeParameterSyntaxChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirTypeAlias>> getTypeAliasCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirTopLevelTypeAliasChecker.INSTANCE, FirActualTypeAliasChecker.INSTANCE, FirActualTypealiasToSpecialAnnotationChecker.INSTANCE, FirTypeAliasExpandsToArrayOfNothingsChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirAnonymousFunction>> getAnonymousFunctionCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirAnonymousFunctionParametersChecker.INSTANCE, FirAnonymousFunctionSyntaxChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirAnonymousInitializer>> getAnonymousInitializerCheckers() {
        return SetsKt.setOf(FirAnonymousInitializerInInterfaceChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirValueParameter>> getValueParameterCheckers() {
        return SetsKt.setOf(FirValueParameterDefaultValueTypeMismatchChecker.INSTANCE);
    }
}
