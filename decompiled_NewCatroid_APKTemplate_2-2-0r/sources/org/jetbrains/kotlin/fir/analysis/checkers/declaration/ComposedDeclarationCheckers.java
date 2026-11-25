package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.analysis.CheckersComponentInternal;
import org.jetbrains.kotlin.fir.analysis.cfa.AbstractFirPropertyInitializationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.cfa.FirControlFlowChecker;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;

/* JADX INFO: compiled from: ComposedDeclarationCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ì\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b+\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010n\u001a\u00020o2\u0006\u0010p\u001a\u00020\u0001H\u0007R\u001e\u0010\u0003\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\t0\u0005j\u0002`\n0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000b\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\f0\u0005j\u0002`\r0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000e\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000f0\u0005j\u0002`\u00100\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0011\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00120\u0005j\u0002`\u00130\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0014\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00150\u0005j\u0002`\u00160\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0017\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00180\u0005j\u0002`\u00190\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u001a\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001b0\u0005j\u0002`\u001c0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u001d\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001e0\u0005j\u0002`\u001f0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\"\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020#0\u0005j\u0002`$0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010%\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020&0\u0005j\u0002`'0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010(\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020)0\u0005j\u0002`*0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010+\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020,0\u0005j\u0002`-0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010.\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020/0\u0005j\u0002`00\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u00101\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002020\u0005j\u0002`30\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u00104\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002050\u0005j\u0002`60\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u00107\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002080\u0005j\u0002`90\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010:\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020;0\u0005j\u0002`<0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010=\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020>0\u0005j\u0002`?0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010@\u001a\b\u0012\u0004\u0012\u00020A0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010B\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bD\u0010ER$\u0010F\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\t0\u0005j\u0002`\n0C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bG\u0010ER$\u0010H\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\f0\u0005j\u0002`\r0C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bI\u0010ER$\u0010J\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000f0\u0005j\u0002`\u00100C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bK\u0010ER$\u0010L\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00120\u0005j\u0002`\u00130C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bM\u0010ER$\u0010N\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00150\u0005j\u0002`\u00160C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bO\u0010ER$\u0010P\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00180\u0005j\u0002`\u00190C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bQ\u0010ER$\u0010R\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001b0\u0005j\u0002`\u001c0C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bS\u0010ER$\u0010T\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001e0\u0005j\u0002`\u001f0C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bU\u0010ER\u001a\u0010V\u001a\b\u0012\u0004\u0012\u00020!0C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bW\u0010ER$\u0010X\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020#0\u0005j\u0002`$0C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bY\u0010ER$\u0010Z\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020&0\u0005j\u0002`'0C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b[\u0010ER$\u0010\\\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020)0\u0005j\u0002`*0C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b]\u0010ER$\u0010^\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020,0\u0005j\u0002`-0C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b_\u0010ER$\u0010`\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020/0\u0005j\u0002`00C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\ba\u0010ER$\u0010b\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002020\u0005j\u0002`30C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bc\u0010ER$\u0010d\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002050\u0005j\u0002`60C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\be\u0010ER$\u0010f\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002080\u0005j\u0002`90C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bg\u0010ER$\u0010h\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020;0\u0005j\u0002`<0C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bi\u0010ER$\u0010j\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020>0\u0005j\u0002`?0C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bk\u0010ER\u001a\u0010l\u001a\b\u0012\u0004\u0012\u00020A0C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bm\u0010E¨\u0006p"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/ComposedDeclarationCheckers;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers;", "()V", "_anonymousFunctionCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirAnonymousFunctionChecker;", "_anonymousInitializerCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirAnonymousInitializerChecker;", "_anonymousObjectCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirAnonymousObjectChecker;", "_backingFieldCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBackingFieldChecker;", "_basicDeclarationCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "_callableDeclarationCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirCallableDeclarationChecker;", "_classCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "_classLikeCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassLikeChecker;", "_constructorCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirConstructorChecker;", "_controlFlowAnalyserCheckers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/cfa/FirControlFlowChecker;", "_enumEntryCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirEnumEntryChecker;", "_fileCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFileChecker;", "_functionCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFunctionChecker;", "_propertyAccessorCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyAccessorChecker;", "_propertyCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyChecker;", "_regularClassCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "_simpleFunctionCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirSimpleFunctionChecker;", "_typeAliasCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirTypeAliasChecker;", "_typeParameterCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirTypeParameterChecker;", "_valueParameterCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirValueParameterChecker;", "_variableAssignmentCfaBasedCheckers", "Lorg/jetbrains/kotlin/fir/analysis/cfa/AbstractFirPropertyInitializationChecker;", "anonymousFunctionCheckers", "", "getAnonymousFunctionCheckers", "()Ljava/util/Set;", "anonymousInitializerCheckers", "getAnonymousInitializerCheckers", "anonymousObjectCheckers", "getAnonymousObjectCheckers", "backingFieldCheckers", "getBackingFieldCheckers", "basicDeclarationCheckers", "getBasicDeclarationCheckers", "callableDeclarationCheckers", "getCallableDeclarationCheckers", "classCheckers", "getClassCheckers", "classLikeCheckers", "getClassLikeCheckers", "constructorCheckers", "getConstructorCheckers", "controlFlowAnalyserCheckers", "getControlFlowAnalyserCheckers", "enumEntryCheckers", "getEnumEntryCheckers", "fileCheckers", "getFileCheckers", "functionCheckers", "getFunctionCheckers", "propertyAccessorCheckers", "getPropertyAccessorCheckers", "propertyCheckers", "getPropertyCheckers", "regularClassCheckers", "getRegularClassCheckers", "simpleFunctionCheckers", "getSimpleFunctionCheckers", "typeAliasCheckers", "getTypeAliasCheckers", "typeParameterCheckers", "getTypeParameterCheckers", "valueParameterCheckers", "getValueParameterCheckers", "variableAssignmentCfaBasedCheckers", "getVariableAssignmentCfaBasedCheckers", "register", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ComposedDeclarationCheckers extends DeclarationCheckers {
    private final Set<FirDeclarationChecker<FirDeclaration>> _basicDeclarationCheckers = new LinkedHashSet();
    private final Set<FirDeclarationChecker<FirCallableDeclaration>> _callableDeclarationCheckers = new LinkedHashSet();
    private final Set<FirDeclarationChecker<FirFunction>> _functionCheckers = new LinkedHashSet();
    private final Set<FirDeclarationChecker<FirSimpleFunction>> _simpleFunctionCheckers = new LinkedHashSet();
    private final Set<FirDeclarationChecker<FirProperty>> _propertyCheckers = new LinkedHashSet();
    private final Set<FirDeclarationChecker<FirClassLikeDeclaration>> _classLikeCheckers = new LinkedHashSet();
    private final Set<FirDeclarationChecker<FirClass>> _classCheckers = new LinkedHashSet();
    private final Set<FirDeclarationChecker<FirRegularClass>> _regularClassCheckers = new LinkedHashSet();
    private final Set<FirDeclarationChecker<FirConstructor>> _constructorCheckers = new LinkedHashSet();
    private final Set<FirDeclarationChecker<FirFile>> _fileCheckers = new LinkedHashSet();
    private final Set<FirDeclarationChecker<FirTypeParameter>> _typeParameterCheckers = new LinkedHashSet();
    private final Set<FirDeclarationChecker<FirTypeAlias>> _typeAliasCheckers = new LinkedHashSet();
    private final Set<FirDeclarationChecker<FirAnonymousFunction>> _anonymousFunctionCheckers = new LinkedHashSet();
    private final Set<FirDeclarationChecker<FirPropertyAccessor>> _propertyAccessorCheckers = new LinkedHashSet();
    private final Set<FirDeclarationChecker<FirBackingField>> _backingFieldCheckers = new LinkedHashSet();
    private final Set<FirDeclarationChecker<FirValueParameter>> _valueParameterCheckers = new LinkedHashSet();
    private final Set<FirDeclarationChecker<FirEnumEntry>> _enumEntryCheckers = new LinkedHashSet();
    private final Set<FirDeclarationChecker<FirAnonymousObject>> _anonymousObjectCheckers = new LinkedHashSet();
    private final Set<FirDeclarationChecker<FirAnonymousInitializer>> _anonymousInitializerCheckers = new LinkedHashSet();
    private final Set<FirControlFlowChecker> _controlFlowAnalyserCheckers = new LinkedHashSet();
    private final Set<AbstractFirPropertyInitializationChecker> _variableAssignmentCfaBasedCheckers = new LinkedHashSet();

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirDeclaration>> getBasicDeclarationCheckers() {
        return this._basicDeclarationCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirCallableDeclaration>> getCallableDeclarationCheckers() {
        return this._callableDeclarationCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirFunction>> getFunctionCheckers() {
        return this._functionCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirSimpleFunction>> getSimpleFunctionCheckers() {
        return this._simpleFunctionCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirProperty>> getPropertyCheckers() {
        return this._propertyCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirClassLikeDeclaration>> getClassLikeCheckers() {
        return this._classLikeCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirClass>> getClassCheckers() {
        return this._classCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirRegularClass>> getRegularClassCheckers() {
        return this._regularClassCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirConstructor>> getConstructorCheckers() {
        return this._constructorCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirFile>> getFileCheckers() {
        return this._fileCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirTypeParameter>> getTypeParameterCheckers() {
        return this._typeParameterCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirTypeAlias>> getTypeAliasCheckers() {
        return this._typeAliasCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirAnonymousFunction>> getAnonymousFunctionCheckers() {
        return this._anonymousFunctionCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirPropertyAccessor>> getPropertyAccessorCheckers() {
        return this._propertyAccessorCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirBackingField>> getBackingFieldCheckers() {
        return this._backingFieldCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirValueParameter>> getValueParameterCheckers() {
        return this._valueParameterCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirEnumEntry>> getEnumEntryCheckers() {
        return this._enumEntryCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirAnonymousObject>> getAnonymousObjectCheckers() {
        return this._anonymousObjectCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirAnonymousInitializer>> getAnonymousInitializerCheckers() {
        return this._anonymousInitializerCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirControlFlowChecker> getControlFlowAnalyserCheckers() {
        return this._controlFlowAnalyserCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<AbstractFirPropertyInitializationChecker> getVariableAssignmentCfaBasedCheckers() {
        return this._variableAssignmentCfaBasedCheckers;
    }

    @CheckersComponentInternal
    public final void register(DeclarationCheckers checkers) {
        Intrinsics.checkNotNullParameter(checkers, "checkers");
        CollectionsKt.addAll(this._basicDeclarationCheckers, checkers.getBasicDeclarationCheckers());
        CollectionsKt.addAll(this._callableDeclarationCheckers, checkers.getCallableDeclarationCheckers());
        CollectionsKt.addAll(this._functionCheckers, checkers.getFunctionCheckers());
        CollectionsKt.addAll(this._simpleFunctionCheckers, checkers.getSimpleFunctionCheckers());
        CollectionsKt.addAll(this._propertyCheckers, checkers.getPropertyCheckers());
        CollectionsKt.addAll(this._classLikeCheckers, checkers.getClassLikeCheckers());
        CollectionsKt.addAll(this._classCheckers, checkers.getClassCheckers());
        CollectionsKt.addAll(this._regularClassCheckers, checkers.getRegularClassCheckers());
        CollectionsKt.addAll(this._constructorCheckers, checkers.getConstructorCheckers());
        CollectionsKt.addAll(this._fileCheckers, checkers.getFileCheckers());
        CollectionsKt.addAll(this._typeParameterCheckers, checkers.getTypeParameterCheckers());
        CollectionsKt.addAll(this._typeAliasCheckers, checkers.getTypeAliasCheckers());
        CollectionsKt.addAll(this._anonymousFunctionCheckers, checkers.getAnonymousFunctionCheckers());
        CollectionsKt.addAll(this._propertyAccessorCheckers, checkers.getPropertyAccessorCheckers());
        CollectionsKt.addAll(this._backingFieldCheckers, checkers.getBackingFieldCheckers());
        CollectionsKt.addAll(this._valueParameterCheckers, checkers.getValueParameterCheckers());
        CollectionsKt.addAll(this._enumEntryCheckers, checkers.getEnumEntryCheckers());
        CollectionsKt.addAll(this._anonymousObjectCheckers, checkers.getAnonymousObjectCheckers());
        CollectionsKt.addAll(this._anonymousInitializerCheckers, checkers.getAnonymousInitializerCheckers());
        CollectionsKt.addAll(this._controlFlowAnalyserCheckers, checkers.getControlFlowAnalyserCheckers());
        CollectionsKt.addAll(this._variableAssignmentCfaBasedCheckers, checkers.getVariableAssignmentCfaBasedCheckers());
    }
}
