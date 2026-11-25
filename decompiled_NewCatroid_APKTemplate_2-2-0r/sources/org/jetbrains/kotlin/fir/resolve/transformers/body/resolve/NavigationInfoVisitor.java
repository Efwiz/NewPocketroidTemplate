package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: LocalClassesNavigation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u001a\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\r2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\u001e\u001a\u00020\u00022\u0006\u0010\u001f\u001a\u00020 2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010!\u001a\u00020\u00022\u0006\u0010\"\u001a\u00020#2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010$\u001a\u00020\u00022\u0006\u0010%\u001a\u00020&2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010'\u001a\u00020\u00022\u0006\u0010(\u001a\u00020)2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010*\u001a\u00020\u00022\u0006\u0010+\u001a\u00020,2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001f\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00070\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001d\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00070\t¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000b¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/NavigationInfoVisitor;", "Lorg/jetbrains/kotlin/fir/visitors/FirDefaultVisitor;", "", "", "()V", "currentPath", "", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "parentForClass", "", "getParentForClass", "()Ljava/util/Map;", "resultingMap", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "getResultingMap", "visitAnonymousObject", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "data", "visitCallableDeclaration", "callableDeclaration", "visitClass", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "visitConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "visitField", "field", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "visitProperty", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "visitRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "visitSimpleFunction", "simpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "visitTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class NavigationInfoVisitor extends FirDefaultVisitor<Unit, Object> {
    private final Map<FirCallableDeclaration, FirClassLikeDeclaration> resultingMap = new LinkedHashMap();
    private final Map<FirClassLikeDeclaration, FirClassLikeDeclaration> parentForClass = new LinkedHashMap();
    private final List<FirClassLikeDeclaration> currentPath = new ArrayList();

    /* JADX INFO: renamed from: visitElement, reason: collision with other method in class */
    public void m7065visitElement(FirElement element, Object data) {
        Intrinsics.checkNotNullParameter(element, "element");
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitAnonymousObject(FirAnonymousObject firAnonymousObject, Object obj) {
        m7061visitAnonymousObject(firAnonymousObject, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitCallableDeclaration(FirCallableDeclaration firCallableDeclaration, Object obj) {
        m7062visitCallableDeclaration(firCallableDeclaration, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitClass(FirClass firClass, Object obj) {
        m7063visitClass(firClass, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitConstructor(FirConstructor firConstructor, Object obj) {
        m7064visitConstructor(firConstructor, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitElement(FirElement firElement, Object obj) {
        m7065visitElement(firElement, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitField(FirField firField, Object obj) {
        m7066visitField(firField, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitProperty(FirProperty firProperty, Object obj) {
        m7067visitProperty(firProperty, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitRegularClass(FirRegularClass firRegularClass, Object obj) {
        m7068visitRegularClass(firRegularClass, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitSimpleFunction(FirSimpleFunction firSimpleFunction, Object obj) {
        m7069visitSimpleFunction(firSimpleFunction, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitTypeAlias(FirTypeAlias firTypeAlias, Object obj) {
        m7070visitTypeAlias(firTypeAlias, obj);
        return Unit.INSTANCE;
    }

    public final Map<FirCallableDeclaration, FirClassLikeDeclaration> getResultingMap() {
        return this.resultingMap;
    }

    public final Map<FirClassLikeDeclaration, FirClassLikeDeclaration> getParentForClass() {
        return this.parentForClass;
    }

    /* JADX INFO: renamed from: visitRegularClass, reason: collision with other method in class */
    public void m7068visitRegularClass(FirRegularClass regularClass, Object data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        m7063visitClass((FirClass) regularClass, (Object) null);
    }

    /* JADX INFO: renamed from: visitAnonymousObject, reason: collision with other method in class */
    public void m7061visitAnonymousObject(FirAnonymousObject anonymousObject, Object data) {
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        m7063visitClass((FirClass) anonymousObject, (Object) null);
    }

    /* JADX INFO: renamed from: visitTypeAlias, reason: collision with other method in class */
    public void m7070visitTypeAlias(FirTypeAlias typeAlias, Object data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        this.parentForClass.put(typeAlias, (FirClassLikeDeclaration) CollectionsKt.lastOrNull((List) this.currentPath));
    }

    /* JADX INFO: renamed from: visitClass, reason: collision with other method in class */
    public void m7063visitClass(FirClass klass, Object data) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        this.parentForClass.put(klass, (FirClassLikeDeclaration) CollectionsKt.lastOrNull((List) this.currentPath));
        this.currentPath.add(klass);
        klass.acceptChildren(this, null);
        List<FirClassLikeDeclaration> list = this.currentPath;
        list.remove(list.size() - 1);
    }

    /* JADX INFO: renamed from: visitSimpleFunction, reason: collision with other method in class */
    public void m7069visitSimpleFunction(FirSimpleFunction simpleFunction, Object data) {
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        m7062visitCallableDeclaration((FirCallableDeclaration) simpleFunction, (Object) null);
    }

    /* JADX INFO: renamed from: visitProperty, reason: collision with other method in class */
    public void m7067visitProperty(FirProperty property, Object data) {
        Intrinsics.checkNotNullParameter(property, "property");
        m7062visitCallableDeclaration((FirCallableDeclaration) property, (Object) null);
    }

    /* JADX INFO: renamed from: visitField, reason: collision with other method in class */
    public void m7066visitField(FirField field, Object data) {
        Intrinsics.checkNotNullParameter(field, "field");
        m7062visitCallableDeclaration((FirCallableDeclaration) field, (Object) null);
    }

    /* JADX INFO: renamed from: visitConstructor, reason: collision with other method in class */
    public void m7064visitConstructor(FirConstructor constructor, Object data) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        m7062visitCallableDeclaration((FirCallableDeclaration) constructor, (Object) null);
    }

    /* JADX INFO: renamed from: visitCallableDeclaration, reason: collision with other method in class */
    public void m7062visitCallableDeclaration(FirCallableDeclaration callableDeclaration, Object data) {
        Intrinsics.checkNotNullParameter(callableDeclaration, "callableDeclaration");
        if (callableDeclaration.getReturnTypeRef() instanceof FirImplicitTypeRef) {
            this.resultingMap.put(callableDeclaration, (FirClassLikeDeclaration) CollectionsKt.last((List) this.currentPath));
        }
    }
}
