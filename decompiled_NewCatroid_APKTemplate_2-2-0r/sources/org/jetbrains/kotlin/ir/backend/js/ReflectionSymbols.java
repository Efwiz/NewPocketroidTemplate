package org.jetbrains.kotlin.ir.backend.js;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ReflectionSymbols.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0014\u0010\b\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0005R\u0014\u0010\n\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005R\u0014\u0010\f\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u0005R\u0014\u0010\u000e\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005R\u0012\u0010\u0010\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0005R\u0012\u0010\u0012\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0005R\u0012\u0010\u0014\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0005R\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0005R\u0012\u0010\u0018\u001a\u00020\u0019X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0012\u0010\u001c\u001a\u00020\u0019X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ReflectionSymbols;", "", Namer.CREATE_CONTRAVARIANT_KTYPE_PROJECTION, "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getCreateContravariantKTypeProjection", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", Namer.CREATE_COVARIANT_KTYPE_PROJECTION, "getCreateCovariantKTypeProjection", Namer.CREATE_DYNAMIC_KTYPE, "getCreateDynamicKType", Namer.CREATE_INVARIANT_KTYPE_PROJECTION, "getCreateInvariantKTypeProjection", Namer.CREATE_KTYPE, "getCreateKType", Namer.CREATE_KTYPE_PARAMETER, "getCreateKTypeParameter", "getClassData", "getGetClassData", Namer.GET_KCLASS, "getGetKClass", Namer.GET_KCLASS_FROM_EXPRESSION, "getGetKClassFromExpression", Namer.GET_START_KTYPE_PROJECTION, "getGetStarKTypeProjection", "kTypeClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getKTypeClass", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "primitiveClassesObject", "getPrimitiveClassesObject", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ReflectionSymbols {
    IrSimpleFunctionSymbol getCreateContravariantKTypeProjection();

    IrSimpleFunctionSymbol getCreateCovariantKTypeProjection();

    IrSimpleFunctionSymbol getCreateDynamicKType();

    IrSimpleFunctionSymbol getCreateInvariantKTypeProjection();

    IrSimpleFunctionSymbol getCreateKType();

    IrSimpleFunctionSymbol getCreateKTypeParameter();

    IrSimpleFunctionSymbol getGetClassData();

    IrSimpleFunctionSymbol getGetKClass();

    IrSimpleFunctionSymbol getGetKClassFromExpression();

    IrSimpleFunctionSymbol getGetStarKTypeProjection();

    IrClassSymbol getKTypeClass();

    IrClassSymbol getPrimitiveClassesObject();
}
