package org.jetbrains.kotlin.fir.backend;

import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleUtilsKt;
import org.jetbrains.kotlin.backend.common.serialization.mangle.SpecialDeclarationType;
import org.jetbrains.kotlin.backend.common.serialization.mangle.ir.IrExportCheckerVisitorKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.resolve.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirExportCheckerVisitor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\b\u0012\u0004\u0012\u00020\u00050\u0004B\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0003H\u0016J\u001d\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u000eJ\u001d\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u0012J\u001d\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u0016J\u001d\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u001aJ\u001d\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u001eJ\u001d\u0010\u001f\u001a\u00020\u00022\u0006\u0010 \u001a\u00020!2\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\"J\u001d\u0010#\u001a\u00020\u00022\u0006\u0010$\u001a\u00020%2\u0006\u0010\r\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010&J\u001b\u0010'\u001a\u00020\u0002\"\b\b\u0000\u0010(*\u00020)*\u0002H(H\u0002¢\u0006\u0002\u0010*J\u001b\u0010+\u001a\u00020\u0002\"\b\b\u0000\u0010(*\u00020,*\u0002H(H\u0002¢\u0006\u0002\u0010-J\u001b\u0010+\u001a\u00020\u0002\"\b\b\u0000\u0010(*\u00020.*\u0002H(H\u0002¢\u0006\u0002\u0010/¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/FirExportCheckerVisitor;", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinExportChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "()V", "check", "declaration", ModuleXmlParser.TYPE, "visitAnonymousFunction", "anonymousFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "data", "(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitAnonymousObject", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "(Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "(Lorg/jetbrains/kotlin/fir/FirElement;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitProperty", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "visitSimpleFunction", "simpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "(Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;)Ljava/lang/Boolean;", "globalMemberIsExported", "D", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;)Z", "isExported", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Z", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;)Z", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirExportCheckerVisitor extends FirVisitor<Boolean, SpecialDeclarationType> implements KotlinExportChecker<FirDeclaration> {
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker
    public boolean check(FirDeclaration declaration, SpecialDeclarationType type) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(type, "type");
        return ((Boolean) declaration.accept(this, type)).booleanValue();
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public Boolean visitElement(FirElement element, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        throw new NotImplementedError("An operation is not implemented: Should have not been reached");
    }

    /* JADX WARN: Type inference failed for: r4v5, types: [org.jetbrains.kotlin.fir.declarations.FirDeclaration] */
    private final <D extends FirCallableDeclaration> boolean isExported(D d) {
        ClassId classId = d.getSymbol().getCallableId().getClassId();
        if (classId == null) {
            return globalMemberIsExported(d);
        }
        if (d.getStatus().getVisibility() != Visibilities.Local.INSTANCE) {
            FirClassifierSymbol<?> symbol = FirSymbolProviderKt.toSymbol(classId, d.getModuleData().getSession());
            Intrinsics.checkNotNull(symbol);
            if (((Boolean) symbol.getFir().accept(this, SpecialDeclarationType.REGULAR)).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    private final <D extends FirClassLikeDeclaration> boolean isExported(D d) {
        FirClassLikeDeclaration containingDeclaration = DeclarationUtilsKt.getContainingDeclaration(d, d.getModuleData().getSession());
        if (containingDeclaration == null) {
            return globalMemberIsExported(d);
        }
        return d.getStatus().getVisibility() != Visibilities.Local.INSTANCE && ((Boolean) containingDeclaration.accept(this, SpecialDeclarationType.REGULAR)).booleanValue();
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public Boolean visitSimpleFunction(FirSimpleFunction simpleFunction, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        Intrinsics.checkNotNullParameter(data, "data");
        return Boolean.valueOf(!IrExportCheckerVisitorKt.isAnonymous(simpleFunction.getName()) && isExported(simpleFunction));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public Boolean visitRegularClass(FirRegularClass regularClass, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        Intrinsics.checkNotNullParameter(data, "data");
        if (data == SpecialDeclarationType.ANON_INIT || IrExportCheckerVisitorKt.isAnonymous(regularClass.getName())) {
            return false;
        }
        return Boolean.valueOf(isExported(regularClass));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public Boolean visitConstructor(FirConstructor constructor, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(data, "data");
        FirClassLikeDeclaration firClassLikeDeclarationFirClassLike = DeclarationUtilsKt.firClassLike(constructor.getReturnTypeRef(), constructor.getModuleData().getSession());
        Intrinsics.checkNotNull(firClassLikeDeclarationFirClassLike);
        return Boolean.valueOf(isExported(firClassLikeDeclarationFirClassLike));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public Boolean visitProperty(FirProperty property, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(data, "data");
        return Boolean.valueOf(isExported(property));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public Boolean visitAnonymousFunction(FirAnonymousFunction anonymousFunction, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
        Intrinsics.checkNotNullParameter(data, "data");
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public Boolean visitAnonymousObject(FirAnonymousObject anonymousObject, SpecialDeclarationType data) {
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        Intrinsics.checkNotNullParameter(data, "data");
        return false;
    }

    private final <D extends FirMemberDeclaration> boolean globalMemberIsExported(D d) {
        Visibility visibility = d.getStatus().getVisibility();
        if (visibility.getIsPublicAPI() || visibility == Visibilities.Internal.INSTANCE) {
            return true;
        }
        if (visibility == Visibilities.Local.INSTANCE) {
            return false;
        }
        List<FirAnnotation> annotations = d.getAnnotations();
        ClassId classId = ClassId.topLevel(MangleUtilsKt.getPublishedApiAnnotation());
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        return FirAnnotationUtilsKt.hasAnnotation(annotations, classId, d.getModuleData().getSession()) || isPlatformSpecificExported(d);
    }
}
