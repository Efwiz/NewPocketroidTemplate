package org.jetbrains.kotlin.fir.analysis.js.checkers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsAbstractNativeAnnotationChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsBuiltinNameClashChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsDynamicDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsExportAnnotationChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsExportDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsExternalChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsExternalFileChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsExternalInheritorOnlyChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsInheritanceClassChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsInheritanceFunctionChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsModuleChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsMultipleInheritanceChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsNameCharsChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsNameChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsNativeGetterChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsNativeInvokeChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsNativeSetterChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsPropertyDelegationByDynamicChecker;
import org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsRuntimeAnnotationChecker;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;

/* JADX INFO: compiled from: JsDeclarationCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R$\u0010\u0003\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR$\u0010\n\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000b0\u0005j\u0002`\f0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\tR$\u0010\u000e\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000f0\u0005j\u0002`\u00100\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\tR$\u0010\u0012\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00130\u0005j\u0002`\u00140\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\tR$\u0010\u0016\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00170\u0005j\u0002`\u00180\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\t¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/JsDeclarationCheckers;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers;", "()V", "basicDeclarationCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "getBasicDeclarationCheckers", "()Ljava/util/Set;", "classCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "getClassCheckers", "functionCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFunctionChecker;", "getFunctionCheckers", "propertyCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyChecker;", "getPropertyCheckers", "simpleFunctionCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirSimpleFunctionChecker;", "getSimpleFunctionCheckers", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsDeclarationCheckers extends DeclarationCheckers {
    public static final JsDeclarationCheckers INSTANCE = new JsDeclarationCheckers();

    private JsDeclarationCheckers() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirFunction>> getFunctionCheckers() {
        return SetsKt.setOf(FirJsInheritanceFunctionChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirDeclaration>> getBasicDeclarationCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirJsModuleChecker.INSTANCE, FirJsRuntimeAnnotationChecker.INSTANCE, FirJsExternalChecker.INSTANCE, FirJsExternalFileChecker.INSTANCE, FirJsNameChecker.INSTANCE, FirJsExportAnnotationChecker.INSTANCE, FirJsExportDeclarationChecker.INSTANCE, FirJsBuiltinNameClashChecker.INSTANCE, FirJsNameCharsChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirClass>> getClassCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirJsMultipleInheritanceChecker.INSTANCE, FirJsDynamicDeclarationChecker.INSTANCE, FirJsInheritanceClassChecker.INSTANCE, FirJsExternalInheritorOnlyChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirSimpleFunction>> getSimpleFunctionCheckers() {
        return SetsKt.setOf((Object[]) new FirJsAbstractNativeAnnotationChecker[]{FirJsNativeInvokeChecker.INSTANCE, FirJsNativeGetterChecker.INSTANCE, FirJsNativeSetterChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirProperty>> getPropertyCheckers() {
        return SetsKt.setOf(FirJsPropertyDelegationByDynamicChecker.INSTANCE);
    }
}
