package org.jetbrains.kotlin.fir.analysis.collectors.components;

import androidx.exifinterface.media.ExifInterface;
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
import org.jetbrains.kotlin.fir.analysis.checkers.type.FirTypeChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.type.TypeCheckers;
import org.jetbrains.kotlin.fir.types.FirDynamicTypeRef;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeRef;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirIntersectionTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;

/* JADX INFO: compiled from: TypeCheckersDiagnosticComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010!\u001a\u00020\n2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010$\u001a\u00020\n2\u0006\u0010%\u001a\u00020&2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010'\u001a\u00020\n2\u0006\u0010(\u001a\u00020)2\u0006\u0010\r\u001a\u00020\u000eH\u0016J7\u0010*\u001a\u00020\n\"\b\b\u0000\u0010+*\u00020#*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H+0-0,2\u0006\u0010\"\u001a\u0002H+2\u0006\u0010.\u001a\u00020\u000eH\u0002¢\u0006\u0002\u0010/R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/collectors/components/TypeCheckersDiagnosticComponent;", "Lorg/jetbrains/kotlin/fir/analysis/collectors/components/AbstractDiagnosticCollectorComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/TypeCheckers;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/fir/analysis/checkers/type/TypeCheckers;)V", "visitDynamicTypeRef", "", "dynamicTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirDynamicTypeRef;", "data", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "visitErrorTypeRef", "errorTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirErrorTypeRef;", "visitFunctionTypeRef", "functionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;", "visitImplicitTypeRef", "implicitTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirImplicitTypeRef;", "visitIntersectionTypeRef", "intersectionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirIntersectionTypeRef;", "visitResolvedTypeRef", "resolvedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "visitTypeRef", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "visitTypeRefWithNullability", "typeRefWithNullability", "Lorg/jetbrains/kotlin/fir/types/FirTypeRefWithNullability;", "visitUserTypeRef", "userTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;", "check", ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeChecker;", "context", "(Ljava/util/Collection;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;)V"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeCheckersDiagnosticComponent extends AbstractDiagnosticCollectorComponent {
    private final TypeCheckers checkers;

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitDynamicTypeRef(FirDynamicTypeRef firDynamicTypeRef, CheckerContext checkerContext) {
        visitDynamicTypeRef2(firDynamicTypeRef, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.collectors.components.AbstractDiagnosticCollectorComponent, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitElement(FirElement firElement, CheckerContext checkerContext) {
        visitElement2(firElement, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitErrorTypeRef(FirErrorTypeRef firErrorTypeRef, CheckerContext checkerContext) {
        visitErrorTypeRef2(firErrorTypeRef, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitFunctionTypeRef(FirFunctionTypeRef firFunctionTypeRef, CheckerContext checkerContext) {
        visitFunctionTypeRef2(firFunctionTypeRef, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitImplicitTypeRef(FirImplicitTypeRef firImplicitTypeRef, CheckerContext checkerContext) {
        visitImplicitTypeRef2(firImplicitTypeRef, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitIntersectionTypeRef(FirIntersectionTypeRef firIntersectionTypeRef, CheckerContext checkerContext) {
        visitIntersectionTypeRef2(firIntersectionTypeRef, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitResolvedTypeRef(FirResolvedTypeRef firResolvedTypeRef, CheckerContext checkerContext) {
        visitResolvedTypeRef2(firResolvedTypeRef, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitTypeRef(FirTypeRef firTypeRef, CheckerContext checkerContext) {
        visitTypeRef2(firTypeRef, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitTypeRefWithNullability(FirTypeRefWithNullability firTypeRefWithNullability, CheckerContext checkerContext) {
        visitTypeRefWithNullability2(firTypeRefWithNullability, checkerContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitUserTypeRef(FirUserTypeRef firUserTypeRef, CheckerContext checkerContext) {
        visitUserTypeRef2(firUserTypeRef, checkerContext);
        return Unit.INSTANCE;
    }

    public /* synthetic */ TypeCheckersDiagnosticComponent(FirSession firSession, DiagnosticReporter diagnosticReporter, TypeCheckers typeCheckers, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, diagnosticReporter, (i & 4) != 0 ? CheckersComponentKt.getCheckersComponent(firSession).getTypeCheckers() : typeCheckers);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TypeCheckersDiagnosticComponent(FirSession session, DiagnosticReporter reporter, TypeCheckers checkers) {
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
        if (element instanceof FirTypeRef) {
            throw new IllegalStateException((Reflection.getOrCreateKotlinClass(element.getClass()).getSimpleName() + " should call parent checkers inside " + Reflection.getOrCreateKotlinClass(getClass()).getSimpleName()).toString());
        }
    }

    /* JADX INFO: renamed from: visitDynamicTypeRef, reason: avoid collision after fix types in other method */
    public void visitDynamicTypeRef2(FirDynamicTypeRef dynamicTypeRef, CheckerContext data) {
        Intrinsics.checkNotNullParameter(dynamicTypeRef, "dynamicTypeRef");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllTypeRefCheckers$checkers(), dynamicTypeRef, data);
    }

    /* JADX INFO: renamed from: visitFunctionTypeRef, reason: avoid collision after fix types in other method */
    public void visitFunctionTypeRef2(FirFunctionTypeRef functionTypeRef, CheckerContext data) {
        Intrinsics.checkNotNullParameter(functionTypeRef, "functionTypeRef");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllTypeRefCheckers$checkers(), functionTypeRef, data);
    }

    /* JADX INFO: renamed from: visitUserTypeRef, reason: avoid collision after fix types in other method */
    public void visitUserTypeRef2(FirUserTypeRef userTypeRef, CheckerContext data) {
        Intrinsics.checkNotNullParameter(userTypeRef, "userTypeRef");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllTypeRefCheckers$checkers(), userTypeRef, data);
    }

    /* JADX INFO: renamed from: visitResolvedTypeRef, reason: avoid collision after fix types in other method */
    public void visitResolvedTypeRef2(FirResolvedTypeRef resolvedTypeRef, CheckerContext data) {
        Intrinsics.checkNotNullParameter(resolvedTypeRef, "resolvedTypeRef");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllTypeRefCheckers$checkers(), resolvedTypeRef, data);
    }

    /* JADX INFO: renamed from: visitErrorTypeRef, reason: avoid collision after fix types in other method */
    public void visitErrorTypeRef2(FirErrorTypeRef errorTypeRef, CheckerContext data) {
        Intrinsics.checkNotNullParameter(errorTypeRef, "errorTypeRef");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllTypeRefCheckers$checkers(), errorTypeRef, data);
    }

    /* JADX INFO: renamed from: visitTypeRefWithNullability, reason: avoid collision after fix types in other method */
    public void visitTypeRefWithNullability2(FirTypeRefWithNullability typeRefWithNullability, CheckerContext data) {
        Intrinsics.checkNotNullParameter(typeRefWithNullability, "typeRefWithNullability");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllTypeRefCheckers$checkers(), typeRefWithNullability, data);
    }

    /* JADX INFO: renamed from: visitIntersectionTypeRef, reason: avoid collision after fix types in other method */
    public void visitIntersectionTypeRef2(FirIntersectionTypeRef intersectionTypeRef, CheckerContext data) {
        Intrinsics.checkNotNullParameter(intersectionTypeRef, "intersectionTypeRef");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllTypeRefCheckers$checkers(), intersectionTypeRef, data);
    }

    /* JADX INFO: renamed from: visitImplicitTypeRef, reason: avoid collision after fix types in other method */
    public void visitImplicitTypeRef2(FirImplicitTypeRef implicitTypeRef, CheckerContext data) {
        Intrinsics.checkNotNullParameter(implicitTypeRef, "implicitTypeRef");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllTypeRefCheckers$checkers(), implicitTypeRef, data);
    }

    /* JADX INFO: renamed from: visitTypeRef, reason: avoid collision after fix types in other method */
    public void visitTypeRef2(FirTypeRef typeRef, CheckerContext data) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(data, "data");
        check(this.checkers.getAllTypeRefCheckers$checkers(), typeRef, data);
    }

    private final <T extends FirTypeRef> void check(Collection<? extends FirTypeChecker<? super T>> collection, T t, CheckerContext checkerContext) {
        Iterator<? extends FirTypeChecker<? super T>> it = collection.iterator();
        while (it.hasNext()) {
            it.next().check(t, checkerContext, getReporter());
        }
    }
}
