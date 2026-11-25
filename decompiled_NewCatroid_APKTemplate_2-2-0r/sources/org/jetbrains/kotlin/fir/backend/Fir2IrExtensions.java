package org.jetbrains.kotlin.fir.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource;
import org.jetbrains.kotlin.ir.util.SymbolTable;

/* JADX INFO: compiled from: Fir2IrExtensions.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0018J\u0018\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&J\u001a\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H&J\u001a\u0010\u0011\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\nH&J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0019À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "", "irNeedsDeserialization", "", "getIrNeedsDeserialization", "()Z", "deserializeToplevelClass", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "components", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "findInjectedValue", "Lorg/jetbrains/kotlin/fir/backend/InjectedValue;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "conversionScope", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConversionScope;", "generateOrGetFacadeClass", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrMemberWithContainerSource;", "registerDeclarations", "", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "Default", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Fir2IrExtensions {
    boolean deserializeToplevelClass(IrClass irClass, Fir2IrComponents components);

    /* JADX INFO: renamed from: findInjectedValue */
    InjectedValue mo6785findInjectedValue(FirReference calleeReference, Fir2IrConversionScope conversionScope);

    IrClass generateOrGetFacadeClass(IrMemberWithContainerSource declaration, Fir2IrComponents components);

    boolean getIrNeedsDeserialization();

    void registerDeclarations(SymbolTable symbolTable);

    /* JADX INFO: compiled from: Fir2IrExtensions.kt */
    @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001a\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u001a\u0010\u0012\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions$Default;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrExtensions;", "()V", "irNeedsDeserialization", "", "getIrNeedsDeserialization", "()Z", "deserializeToplevelClass", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "components", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "findInjectedValue", "", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "conversionScope", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrConversionScope;", "generateOrGetFacadeClass", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrMemberWithContainerSource;", "registerDeclarations", "", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements Fir2IrExtensions {
        public static final Default INSTANCE = new Default();

        @Override // org.jetbrains.kotlin.fir.backend.Fir2IrExtensions
        public boolean deserializeToplevelClass(IrClass irClass, Fir2IrComponents components) {
            Intrinsics.checkNotNullParameter(irClass, "irClass");
            Intrinsics.checkNotNullParameter(components, "components");
            return false;
        }

        public Void findInjectedValue(FirReference calleeReference, Fir2IrConversionScope conversionScope) {
            Intrinsics.checkNotNullParameter(calleeReference, "calleeReference");
            Intrinsics.checkNotNullParameter(conversionScope, "conversionScope");
            return null;
        }

        @Override // org.jetbrains.kotlin.fir.backend.Fir2IrExtensions
        public IrClass generateOrGetFacadeClass(IrMemberWithContainerSource declaration, Fir2IrComponents components) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(components, "components");
            return null;
        }

        @Override // org.jetbrains.kotlin.fir.backend.Fir2IrExtensions
        public boolean getIrNeedsDeserialization() {
            return false;
        }

        @Override // org.jetbrains.kotlin.fir.backend.Fir2IrExtensions
        public void registerDeclarations(SymbolTable symbolTable) {
            Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        }

        private Default() {
        }

        @Override // org.jetbrains.kotlin.fir.backend.Fir2IrExtensions
        /* JADX INFO: renamed from: findInjectedValue, reason: collision with other method in class */
        public /* bridge */ /* synthetic */ InjectedValue mo6785findInjectedValue(FirReference firReference, Fir2IrConversionScope fir2IrConversionScope) {
            return (InjectedValue) findInjectedValue(firReference, fir2IrConversionScope);
        }
    }
}
