package org.jetbrains.kotlin.fir.analysis.collectors.components;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.CheckersComponentKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDanglingModifierList;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirErrorProperty;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: DeclarationCheckersDiagnosticComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000æ\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010!\u001a\u00020\n2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010$\u001a\u00020\n2\u0006\u0010%\u001a\u00020&2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010'\u001a\u00020\n2\u0006\u0010(\u001a\u00020)2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010*\u001a\u00020\n2\u0006\u0010+\u001a\u00020,2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010-\u001a\u00020\n2\u0006\u0010.\u001a\u00020/2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u00100\u001a\u00020\n2\u0006\u00101\u001a\u0002022\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u00103\u001a\u00020\n2\u0006\u00104\u001a\u0002052\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u00106\u001a\u00020\n2\u0006\u00107\u001a\u0002082\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u00109\u001a\u00020\n2\u0006\u0010:\u001a\u00020;2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010<\u001a\u00020\n2\u0006\u0010=\u001a\u00020>2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010?\u001a\u00020\n2\u0006\u0010@\u001a\u00020A2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010B\u001a\u00020\n2\u0006\u0010C\u001a\u00020D2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010E\u001a\u00020\n2\u0006\u0010F\u001a\u00020G2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010H\u001a\u00020\n2\u0006\u0010I\u001a\u00020J2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010K\u001a\u00020\n2\u0006\u0010L\u001a\u00020M2\u0006\u0010\r\u001a\u00020\u000eH\u0016J7\u0010N\u001a\u00020\n\"\b\b\u0000\u0010O*\u00020&*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002HO0Q0P2\u0006\u0010%\u001a\u0002HO2\u0006\u0010R\u001a\u00020\u000eH\u0002¢\u0006\u0002\u0010SR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/collectors/components/DeclarationCheckersDiagnosticComponent;", "Lorg/jetbrains/kotlin/fir/analysis/collectors/components/AbstractDiagnosticCollectorComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers;)V", "visitAnonymousFunction", "", "anonymousFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "data", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "visitAnonymousInitializer", "anonymousInitializer", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "visitAnonymousObject", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "visitBackingField", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "visitClass", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "visitCodeFragment", "codeFragment", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "visitConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "visitDanglingModifierList", "danglingModifierList", "Lorg/jetbrains/kotlin/fir/declarations/FirDanglingModifierList;", "visitDeclaration", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "visitEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "visitErrorProperty", "errorProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorProperty;", "visitField", "field", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "visitFile", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "visitProperty", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "visitPropertyAccessor", "propertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "visitRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "visitScript", "script", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "visitSimpleFunction", "simpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "visitTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "visitTypeParameter", "typeParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "visitValueParameter", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "check", "D", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "context", "(Ljava/util/Collection;Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;)V"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeclarationCheckersDiagnosticComponent extends AbstractDiagnosticCollectorComponent {
    private final DeclarationCheckers checkers;

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitAnonymousFunction(FirAnonymousFunction firAnonymousFunction, CheckerContext checkerContext) {
        visitAnonymousFunction2(firAnonymousFunction, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitAnonymousInitializer(FirAnonymousInitializer firAnonymousInitializer, CheckerContext checkerContext) {
        visitAnonymousInitializer2(firAnonymousInitializer, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitAnonymousObject(FirAnonymousObject firAnonymousObject, CheckerContext checkerContext) {
        visitAnonymousObject2(firAnonymousObject, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitBackingField(FirBackingField firBackingField, CheckerContext checkerContext) {
        visitBackingField2(firBackingField, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitClass(FirClass firClass, CheckerContext checkerContext) {
        visitClass2(firClass, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitCodeFragment(FirCodeFragment firCodeFragment, CheckerContext checkerContext) {
        visitCodeFragment2(firCodeFragment, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitConstructor(FirConstructor firConstructor, CheckerContext checkerContext) {
        visitConstructor2(firConstructor, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitDanglingModifierList(FirDanglingModifierList firDanglingModifierList, CheckerContext checkerContext) {
        visitDanglingModifierList2(firDanglingModifierList, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitDeclaration(FirDeclaration firDeclaration, CheckerContext checkerContext) {
        visitDeclaration2(firDeclaration, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.collectors.components.AbstractDiagnosticCollectorComponent, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitElement(FirElement firElement, CheckerContext checkerContext) {
        visitElement2(firElement, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitEnumEntry(FirEnumEntry firEnumEntry, CheckerContext checkerContext) {
        visitEnumEntry2(firEnumEntry, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitErrorProperty(FirErrorProperty firErrorProperty, CheckerContext checkerContext) {
        visitErrorProperty2(firErrorProperty, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitField(FirField firField, CheckerContext checkerContext) {
        visitField2(firField, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitFile(FirFile firFile, CheckerContext checkerContext) {
        visitFile2(firFile, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitProperty(FirProperty firProperty, CheckerContext checkerContext) {
        visitProperty2(firProperty, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitPropertyAccessor(FirPropertyAccessor firPropertyAccessor, CheckerContext checkerContext) {
        visitPropertyAccessor2(firPropertyAccessor, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitRegularClass(FirRegularClass firRegularClass, CheckerContext checkerContext) {
        visitRegularClass2(firRegularClass, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitScript(FirScript firScript, CheckerContext checkerContext) {
        visitScript2(firScript, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitSimpleFunction(FirSimpleFunction firSimpleFunction, CheckerContext checkerContext) {
        visitSimpleFunction2(firSimpleFunction, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitTypeAlias(FirTypeAlias firTypeAlias, CheckerContext checkerContext) {
        visitTypeAlias2(firTypeAlias, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitTypeParameter(FirTypeParameter firTypeParameter, CheckerContext checkerContext) {
        visitTypeParameter2(firTypeParameter, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitValueParameter(FirValueParameter firValueParameter, CheckerContext checkerContext) {
        visitValueParameter2(firValueParameter, checkerContext);
        return Unit.INSTANCE;
    }

    public /* synthetic */ DeclarationCheckersDiagnosticComponent(FirSession firSession, DiagnosticReporter diagnosticReporter, DeclarationCheckers declarationCheckers, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, diagnosticReporter, (i & 4) != 0 ? CheckersComponentKt.getCheckersComponent(firSession).getDeclarationCheckers() : declarationCheckers);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeclarationCheckersDiagnosticComponent(FirSession session, DiagnosticReporter reporter, DeclarationCheckers checkers) {
        super(session, reporter);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(checkers, "checkers");
        this.checkers = checkers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.collectors.components.AbstractDiagnosticCollectorComponent
    /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
    public void visitElement2(FirElement element, CheckerContext data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        if (element instanceof FirDeclaration) {
            throw new IllegalStateException((Reflection.getOrCreateKotlinClass(element.getClass()).getSimpleName() + " should call parent checkers inside " + Reflection.getOrCreateKotlinClass(getClass()).getSimpleName()).toString());
        }
    }

    /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
    public void visitDeclaration2(FirDeclaration declaration, CheckerContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicDeclarationCheckers$checkers(), declaration, data);
    }

    /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
    public void visitFile2(FirFile file, CheckerContext data) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllFileCheckers$checkers(), file, data);
    }

    /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
    public void visitProperty2(FirProperty property, CheckerContext data) {
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllPropertyCheckers$checkers(), property, data);
    }

    /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
    public void visitClass2(FirClass klass, CheckerContext data) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllClassCheckers$checkers(), klass, data);
    }

    /* JADX INFO: renamed from: visitRegularClass, reason: avoid collision after fix types in other method */
    public void visitRegularClass2(FirRegularClass regularClass, CheckerContext data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllRegularClassCheckers$checkers(), regularClass, data);
    }

    /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
    public void visitSimpleFunction2(FirSimpleFunction simpleFunction, CheckerContext data) {
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllSimpleFunctionCheckers$checkers(), simpleFunction, data);
    }

    /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
    public void visitTypeAlias2(FirTypeAlias typeAlias, CheckerContext data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllTypeAliasCheckers$checkers(), typeAlias, data);
    }

    /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
    public void visitConstructor2(FirConstructor constructor, CheckerContext data) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllConstructorCheckers$checkers(), constructor, data);
    }

    /* JADX INFO: renamed from: visitAnonymousFunction, reason: avoid collision after fix types in other method */
    public void visitAnonymousFunction2(FirAnonymousFunction anonymousFunction, CheckerContext data) {
        Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllAnonymousFunctionCheckers$checkers(), anonymousFunction, data);
    }

    /* JADX INFO: renamed from: visitPropertyAccessor, reason: avoid collision after fix types in other method */
    public void visitPropertyAccessor2(FirPropertyAccessor propertyAccessor, CheckerContext data) {
        Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllPropertyAccessorCheckers$checkers(), propertyAccessor, data);
    }

    /* JADX INFO: renamed from: visitBackingField, reason: avoid collision after fix types in other method */
    public void visitBackingField2(FirBackingField backingField, CheckerContext data) {
        Intrinsics.checkNotNullParameter(backingField, "backingField");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBackingFieldCheckers$checkers(), backingField, data);
    }

    /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
    public void visitValueParameter2(FirValueParameter valueParameter, CheckerContext data) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllValueParameterCheckers$checkers(), valueParameter, data);
    }

    /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
    public void visitTypeParameter2(FirTypeParameter typeParameter, CheckerContext data) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllTypeParameterCheckers$checkers(), typeParameter, data);
    }

    /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
    public void visitEnumEntry2(FirEnumEntry enumEntry, CheckerContext data) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllEnumEntryCheckers$checkers(), enumEntry, data);
    }

    /* JADX INFO: renamed from: visitAnonymousObject, reason: avoid collision after fix types in other method */
    public void visitAnonymousObject2(FirAnonymousObject anonymousObject, CheckerContext data) {
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllAnonymousObjectCheckers$checkers(), anonymousObject, data);
    }

    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
    public void visitAnonymousInitializer2(FirAnonymousInitializer anonymousInitializer, CheckerContext data) {
        Intrinsics.checkNotNullParameter(anonymousInitializer, "anonymousInitializer");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllAnonymousInitializerCheckers$checkers(), anonymousInitializer, data);
    }

    /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
    public void visitField2(FirField field, CheckerContext data) {
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllCallableDeclarationCheckers$checkers(), field, data);
    }

    /* JADX INFO: renamed from: visitDanglingModifierList, reason: avoid collision after fix types in other method */
    public void visitDanglingModifierList2(FirDanglingModifierList danglingModifierList, CheckerContext data) {
        Intrinsics.checkNotNullParameter(danglingModifierList, "danglingModifierList");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicDeclarationCheckers$checkers(), danglingModifierList, data);
    }

    /* JADX INFO: renamed from: visitErrorProperty, reason: avoid collision after fix types in other method */
    public void visitErrorProperty2(FirErrorProperty errorProperty, CheckerContext data) {
        Intrinsics.checkNotNullParameter(errorProperty, "errorProperty");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllCallableDeclarationCheckers$checkers(), errorProperty, data);
    }

    /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
    public void visitScript2(FirScript script, CheckerContext data) {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicDeclarationCheckers$checkers(), script, data);
    }

    /* JADX INFO: renamed from: visitCodeFragment, reason: avoid collision after fix types in other method */
    public void visitCodeFragment2(FirCodeFragment codeFragment, CheckerContext data) {
        Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllBasicDeclarationCheckers$checkers(), codeFragment, data);
    }

    private final <D extends FirDeclaration> void check(Collection<? extends FirDeclarationChecker<? super D>> collection, D d, CheckerContext checkerContext) {
        Iterator<? extends FirDeclarationChecker<? super D>> it = collection.iterator();
        while (it.hasNext()) {
            it.next().check(d, checkerContext, getReporter());
        }
    }
}
