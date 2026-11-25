package org.jetbrains.kotlin.ir.backend.js;

import kotlin.Metadata;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: JsCommonBackendContext.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H&R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\u0017X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0012\u0010\u001a\u001a\u00020\u001bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0012\u0010\u001e\u001a\u00020\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0012\u0010\"\u001a\u00020#X¦\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0014\u0010&\u001a\u0004\u0018\u00010\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b'\u0010\rR\u0014\u0010(\u001a\u0004\u0018\u00010\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b)\u0010\rø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006.À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "catchAllThrowableType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getCatchAllThrowableType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "coroutineSymbols", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonCoroutineSymbols;", "getCoroutineSymbols", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonCoroutineSymbols;", "createEnumEntries", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getCreateEnumEntries", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "enumEntries", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getEnumEntries", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "es6mode", "", "getEs6mode", "()Z", "inlineClassesUtils", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonInlineClassesUtils;", "getInlineClassesUtils", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonInlineClassesUtils;", "mapping", "Lorg/jetbrains/kotlin/ir/backend/js/JsMapping;", "getMapping", "()Lorg/jetbrains/kotlin/ir/backend/js/JsMapping;", "propertyLazyInitialization", "Lorg/jetbrains/kotlin/ir/backend/js/PropertyLazyInitialization;", "getPropertyLazyInitialization", "()Lorg/jetbrains/kotlin/ir/backend/js/PropertyLazyInitialization;", "reflectionSymbols", "Lorg/jetbrains/kotlin/ir/backend/js/ReflectionSymbols;", "getReflectionSymbols", "()Lorg/jetbrains/kotlin/ir/backend/js/ReflectionSymbols;", "suiteFun", "getSuiteFun", "testFun", "getTestFun", "createTestContainerFun", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JsCommonBackendContext extends CommonBackendContext {
    IrSimpleFunction createTestContainerFun(IrFile irFile);

    JsCommonCoroutineSymbols getCoroutineSymbols();

    IrSimpleFunctionSymbol getCreateEnumEntries();

    IrClassSymbol getEnumEntries();

    default boolean getEs6mode() {
        return false;
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    JsCommonInlineClassesUtils getInlineClassesUtils();

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    JsMapping getMapping();

    PropertyLazyInitialization getPropertyLazyInitialization();

    ReflectionSymbols getReflectionSymbols();

    IrSimpleFunctionSymbol getSuiteFun();

    IrSimpleFunctionSymbol getTestFun();

    default IrType getCatchAllThrowableType() {
        return getIrBuiltIns().getThrowableType();
    }
}
