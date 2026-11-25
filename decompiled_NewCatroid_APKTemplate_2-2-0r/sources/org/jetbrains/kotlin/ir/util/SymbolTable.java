package org.jetbrains.kotlin.ir.util;

import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazySymbolTable;
import org.jetbrains.kotlin.ir.symbols.IrBindableSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrClassPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrClassSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrConstructorPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrConstructorSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrEnumEntryPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrEnumEntrySymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrFieldPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrFieldSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrPropertyPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrPropertySymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeAliasPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeAliasSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeParameterPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeParameterSymbolImpl;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: SymbolTable.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000È\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0002\b!\b\u0016\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010A\u001a\u00020\u0019H\u0014J0\u0010B\u001a\u00020\u00132\u0006\u0010C\u001a\u00020\u00122\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00140E2\u0012\u0010F\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00130GJ0\u0010H\u001a\u00020\u00132\u0006\u0010C\u001a\u00020\u00122\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00140E2\u0012\u0010F\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00130GJ\u0016\u0010I\u001a\u00020J2\u0006\u0010C\u001a\u00020\u00122\u0006\u0010K\u001a\u00020\u0014J0\u0010L\u001a\u00020\u00162\u0006\u0010C\u001a\u00020\u00122\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00170E2\u0012\u0010M\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00160GJ0\u0010N\u001a\u00020\u00162\u0006\u0010C\u001a\u00020\u00122\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00170E2\u0012\u0010M\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00160GJ\u0016\u0010O\u001a\u00020J2\u0006\u0010C\u001a\u00020\u00122\u0006\u0010K\u001a\u00020\u0017J0\u0010P\u001a\u00020\u001e2\u0006\u0010C\u001a\u00020\u00122\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u001f0E2\u0012\u0010Q\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u001e0GJ0\u0010R\u001a\u00020\u001e2\u0006\u0010C\u001a\u00020\u00122\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u001f0E2\u0012\u0010F\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u001e0GJ0\u0010S\u001a\u00020!2\u0006\u0010C\u001a\u00020\u00122\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\"0E2\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020!0GJ\u0016\u0010U\u001a\u00020J2\u0006\u0010C\u001a\u00020\u00122\u0006\u0010K\u001a\u00020\"J0\u0010V\u001a\u00020'2\u0006\u0010C\u001a\u00020\u00122\f\u0010D\u001a\b\u0012\u0004\u0012\u00020(0E2\u0012\u0010W\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020'0GJ0\u0010X\u001a\u0002072\u0006\u0010C\u001a\u00020\u00122\f\u0010D\u001a\b\u0012\u0004\u0012\u0002080E2\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u0002070GJ0\u0010Y\u001a\u0002072\u0006\u0010C\u001a\u00020\u00122\f\u0010D\u001a\b\u0012\u0004\u0012\u0002080E2\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u0002070GJ\u0016\u0010Z\u001a\u00020J2\u0006\u0010C\u001a\u00020\u00122\u0006\u0010K\u001a\u000208J6\u0010[\u001a\u00020'2\u0006\u0010C\u001a\u00020\u00122\u0012\u0010D\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020(0G2\u0012\u0010W\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020'0GJ0\u0010\\\u001a\u00020:2\u0006\u0010C\u001a\u00020\u00122\f\u0010D\u001a\b\u0012\u0004\u0012\u00020;0E2\u0012\u0010]\u001a\u000e\u0012\u0004\u0012\u00020;\u0012\u0004\u0012\u00020:0GJ0\u0010^\u001a\u00020$2\u0006\u0010C\u001a\u00020\u00122\f\u0010D\u001a\b\u0012\u0004\u0012\u00020%0E2\u0012\u0010_\u001a\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020$0GJ0\u0010`\u001a\u00020$2\u0006\u0010C\u001a\u00020\u00122\f\u0010D\u001a\b\u0012\u0004\u0012\u00020%0E2\u0012\u0010_\u001a\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020$0GJ\u0016\u0010a\u001a\u00020J2\u0006\u0010C\u001a\u00020\u00122\u0006\u0010K\u001a\u00020%J0\u0010b\u001a\u00020?2\u0006\u0010C\u001a\u00020\u00122\f\u0010D\u001a\b\u0012\u0004\u0012\u00020@0E2\u0012\u0010c\u001a\u000e\u0012\u0004\u0012\u00020@\u0012\u0004\u0012\u00020?0GJ0\u0010d\u001a\u00020?2\u0006\u0010C\u001a\u00020\u00122\f\u0010D\u001a\b\u0012\u0004\u0012\u00020@0E2\u0012\u0010e\u001a\u000e\u0012\u0004\u0012\u00020@\u0012\u0004\u0012\u00020?0GJ\u0010\u0010f\u001a\u00020J2\u0006\u0010g\u001a\u00020hH\u0016J\u0010\u0010f\u001a\u00020J2\u0006\u0010K\u001a\u00020\u000bH\u0016J\u001c\u0010i\u001a\u00020J2\u0012\u0010j\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020J0GH\u0007J\u0010\u0010k\u001a\u00020J2\u0006\u0010g\u001a\u00020hH\u0016J\u0010\u0010k\u001a\u00020J2\u0006\u0010K\u001a\u00020\u000bH\u0016J\u0010\u0010l\u001a\u00020\u00142\u0006\u0010C\u001a\u00020\u0012H\u0016J0\u0010l\u001a\u00020\u00142\u0006\u0010C\u001a\u00020\u00122\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00140E2\u0012\u0010F\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00130GJ5\u0010m\u001a\u00020\u00142\u0006\u0010C\u001a\u00020\u00122\f\u0010n\u001a\b\u0012\u0004\u0012\u00020\u00140E2\f\u0010o\u001a\b\u0012\u0004\u0012\u00020\u00140EH\u0081\bø\u0001\u0000¢\u0006\u0002\bpJ\u0010\u0010q\u001a\u00020\u00172\u0006\u0010C\u001a\u00020\u0012H\u0016J\u0010\u0010r\u001a\u0004\u0018\u00010\u00172\u0006\u0010C\u001a\u00020\u0012J5\u0010s\u001a\u00020\u00172\u0006\u0010C\u001a\u00020\u00122\f\u0010n\u001a\b\u0012\u0004\u0012\u00020\u00170E2\f\u0010o\u001a\b\u0012\u0004\u0012\u00020\u00170EH\u0081\bø\u0001\u0000¢\u0006\u0002\btJ\u0010\u0010u\u001a\u00020\u001f2\u0006\u0010C\u001a\u00020\u0012H\u0016J5\u0010v\u001a\u00020\u001f2\u0006\u0010C\u001a\u00020\u00122\f\u0010n\u001a\b\u0012\u0004\u0012\u00020\u001f0E2\f\u0010o\u001a\b\u0012\u0004\u0012\u00020\u001f0EH\u0081\bø\u0001\u0000¢\u0006\u0002\bwJ\u0010\u0010x\u001a\u00020\"2\u0006\u0010C\u001a\u00020\u0012H\u0016J5\u0010y\u001a\u00020\"2\u0006\u0010C\u001a\u00020\u00122\f\u0010n\u001a\b\u0012\u0004\u0012\u00020\"0E2\f\u0010o\u001a\b\u0012\u0004\u0012\u00020\"0EH\u0081\bø\u0001\u0000¢\u0006\u0002\bzJ\u0010\u0010{\u001a\u0002082\u0006\u0010C\u001a\u00020\u0012H\u0016J\u0010\u0010|\u001a\u0004\u0018\u0001082\u0006\u0010C\u001a\u00020\u0012J5\u0010}\u001a\u0002082\u0006\u0010C\u001a\u00020\u00122\f\u0010n\u001a\b\u0012\u0004\u0012\u0002080E2\f\u0010o\u001a\b\u0012\u0004\u0012\u0002080EH\u0081\bø\u0001\u0000¢\u0006\u0002\b~J\u0010\u0010\u007f\u001a\u00020%2\u0006\u0010C\u001a\u00020\u0012H\u0016J\u0011\u0010\u0080\u0001\u001a\u0004\u0018\u00010%2\u0006\u0010C\u001a\u00020\u0012J7\u0010\u0081\u0001\u001a\u00020%2\u0006\u0010C\u001a\u00020\u00122\f\u0010n\u001a\b\u0012\u0004\u0012\u00020%0E2\f\u0010o\u001a\b\u0012\u0004\u0012\u00020%0EH\u0081\bø\u0001\u0000¢\u0006\u0003\b\u0082\u0001J\u0011\u0010\u0083\u0001\u001a\u00020@2\u0006\u0010C\u001a\u00020\u0012H\u0016J7\u0010\u0084\u0001\u001a\u00020@2\u0006\u0010C\u001a\u00020\u00122\f\u0010n\u001a\b\u0012\u0004\u0012\u00020@0E2\f\u0010o\u001a\b\u0012\u0004\u0012\u00020@0EH\u0081\bø\u0001\u0000¢\u0006\u0003\b\u0085\u0001J\u0011\u0010\u0086\u0001\u001a\u00020(2\u0006\u0010C\u001a\u00020\u0012H\u0016J7\u0010\u0087\u0001\u001a\u00020(2\u0006\u0010C\u001a\u00020\u00122\f\u0010n\u001a\b\u0012\u0004\u0012\u00020(0E2\f\u0010o\u001a\b\u0012\u0004\u0012\u00020(0EH\u0081\bø\u0001\u0000¢\u0006\u0003\b\u0088\u0001R \u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR \u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00170\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0018\u001a\u00020\u00198\u0016X\u0097\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u001a\u0010\r\u001a\u0004\b\u001b\u0010\u001cR \u0010\u001d\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001f0\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010 \u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\"0\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010#\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020%0\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010&\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020(0\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0017\u0010+\u001a\u00020,¢\u0006\u000e\n\u0000\u0012\u0004\b-\u0010\r\u001a\u0004\b.\u0010/R\u0011\u00100\u001a\u000201¢\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b4\u00105R \u00106\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u000207\u0012\u0004\u0012\u0002080\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R \u00109\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020:\u0012\u0004\u0012\u00020;0\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b<\u0010=R \u0010>\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020@0\u0011X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0089\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "signaturer", "Lorg/jetbrains/kotlin/ir/util/IdSignatureComposer;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "nameProvider", "Lorg/jetbrains/kotlin/ir/util/NameProvider;", "(Lorg/jetbrains/kotlin/ir/util/IdSignatureComposer;Lorg/jetbrains/kotlin/ir/declarations/IrFactory;Lorg/jetbrains/kotlin/ir/util/NameProvider;)V", "allUnboundSymbols", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getAllUnboundSymbols$annotations", "()V", "getAllUnboundSymbols", "()Ljava/util/Set;", "classSlice", "Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice$Flat;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "constructorSlice", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "descriptorExtension", "Lorg/jetbrains/kotlin/ir/util/DescriptorSymbolTableExtension;", "getDescriptorExtension$annotations", "getDescriptorExtension", "()Lorg/jetbrains/kotlin/ir/util/DescriptorSymbolTableExtension;", "enumEntrySlice", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "fieldSlice", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "functionSlice", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "globalTypeParameterSlice", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "lazyWrapper", "Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazySymbolTable;", "getLazyWrapper$annotations", "getLazyWrapper", "()Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazySymbolTable;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "getNameProvider", "()Lorg/jetbrains/kotlin/ir/util/NameProvider;", "propertySlice", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "scriptSlice", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "getSignaturer", "()Lorg/jetbrains/kotlin/ir/util/IdSignatureComposer;", "typeAliasSlice", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "createDescriptorExtension", "declareClass", "signature", "symbolFactory", "Lkotlin/Function0;", "classFactory", "Lkotlin/Function1;", "declareClassIfNotExists", "declareClassWithSignature", "", NativeSymbol.TYPE_NAME, "declareConstructor", "constructorFactory", "declareConstructorIfNotExists", "declareConstructorWithSignature", "declareEnumEntry", "enumEntryFactory", "declareEnumEntryIfNotExists", "declareField", "propertyFactory", "declareFieldWithSignature", "declareGlobalTypeParameter", "typeParameterFactory", "declareProperty", "declarePropertyIfNotExists", "declarePropertyWithSignature", "declareScopedTypeParameter", "declareScript", "scriptFactory", "declareSimpleFunction", "functionFactory", "declareSimpleFunctionIfNotExists", "declareSimpleFunctionWithSignature", "declareTypeAlias", "factory", "declareTypeAliasIfNotExists", "typeAliasFactory", "enterScope", "owner", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "forEachDeclarationSymbol", "block", "leaveScope", "referenceClass", "referenceClassImpl", "publicSymbolFactory", "privateSymbolFactory", "referenceClassImpl$ir_tree", "referenceConstructor", "referenceConstructorIfAny", "referenceConstructorImpl", "referenceConstructorImpl$ir_tree", "referenceEnumEntry", "referenceEnumEntryImpl", "referenceEnumEntryImpl$ir_tree", "referenceField", "referenceFieldImpl", "referenceFieldImpl$ir_tree", "referenceProperty", "referencePropertyIfAny", "referencePropertyImpl", "referencePropertyImpl$ir_tree", "referenceSimpleFunction", "referenceSimpleFunctionIfAny", "referenceSimpleFunctionImpl", "referenceSimpleFunctionImpl$ir_tree", "referenceTypeAlias", "referenceTypeAliasImpl", "referenceTypeAliasImpl$ir_tree", "referenceTypeParameter", "referenceTypeParameterImpl", "referenceTypeParameterImpl$ir_tree", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SymbolTable implements ReferenceSymbolTable {
    private final SymbolTableSlice.Flat<IdSignature, IrClass, IrClassSymbol> classSlice;
    private final SymbolTableSlice.Flat<IdSignature, IrConstructor, IrConstructorSymbol> constructorSlice;
    private final DescriptorSymbolTableExtension descriptorExtension;
    private final SymbolTableSlice.Flat<IdSignature, IrEnumEntry, IrEnumEntrySymbol> enumEntrySlice;
    private final SymbolTableSlice.Flat<IdSignature, IrField, IrFieldSymbol> fieldSlice;
    private final SymbolTableSlice.Flat<IdSignature, IrSimpleFunction, IrSimpleFunctionSymbol> functionSlice;
    private final SymbolTableSlice.Flat<IdSignature, IrTypeParameter, IrTypeParameterSymbol> globalTypeParameterSlice;
    private final IrFactory irFactory;
    private final IrLazySymbolTable lazyWrapper;
    private final IrLock lock;
    private final NameProvider nameProvider;
    private final SymbolTableSlice.Flat<IdSignature, IrProperty, IrPropertySymbol> propertySlice;
    private final SymbolTableSlice.Flat<IdSignature, IrScript, IrScriptSymbol> scriptSlice;
    private final IdSignatureComposer signaturer;
    private final SymbolTableSlice.Flat<IdSignature, IrTypeAlias, IrTypeAliasSymbol> typeAliasSlice;

    @DelicateSymbolTableApi
    public static /* synthetic */ void getAllUnboundSymbols$annotations() {
    }

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptorExtension$annotations() {
    }

    public static /* synthetic */ void getLazyWrapper$annotations() {
    }

    public SymbolTable(IdSignatureComposer signaturer, IrFactory irFactory, NameProvider nameProvider) {
        Intrinsics.checkNotNullParameter(signaturer, "signaturer");
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        Intrinsics.checkNotNullParameter(nameProvider, "nameProvider");
        this.signaturer = signaturer;
        this.irFactory = irFactory;
        this.nameProvider = nameProvider;
        IrLock irLock = new IrLock();
        this.lock = irLock;
        this.scriptSlice = SymbolTableKt.IdSignatureSymbolTableSlice(irLock);
        this.classSlice = SymbolTableKt.IdSignatureSymbolTableSlice(irLock);
        this.constructorSlice = SymbolTableKt.IdSignatureSymbolTableSlice(irLock);
        this.enumEntrySlice = SymbolTableKt.IdSignatureSymbolTableSlice(irLock);
        this.fieldSlice = SymbolTableKt.IdSignatureSymbolTableSlice(irLock);
        this.functionSlice = SymbolTableKt.IdSignatureSymbolTableSlice(irLock);
        this.propertySlice = SymbolTableKt.IdSignatureSymbolTableSlice(irLock);
        this.typeAliasSlice = SymbolTableKt.IdSignatureSymbolTableSlice(irLock);
        this.globalTypeParameterSlice = SymbolTableKt.IdSignatureSymbolTableSlice(irLock);
        this.lazyWrapper = new IrLazySymbolTable(this);
        this.descriptorExtension = createDescriptorExtension();
    }

    public final IdSignatureComposer getSignaturer() {
        return this.signaturer;
    }

    public final IrFactory getIrFactory() {
        return this.irFactory;
    }

    public /* synthetic */ SymbolTable(IdSignatureComposer idSignatureComposer, IrFactory irFactory, NameProvider.DEFAULT r3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(idSignatureComposer, irFactory, (i & 4) != 0 ? NameProvider.DEFAULT.INSTANCE : r3);
    }

    public final NameProvider getNameProvider() {
        return this.nameProvider;
    }

    public final IrLock getLock() {
        return this.lock;
    }

    public final IrLazySymbolTable getLazyWrapper() {
        return this.lazyWrapper;
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public DescriptorSymbolTableExtension getDescriptorExtension() {
        return this.descriptorExtension;
    }

    protected DescriptorSymbolTableExtension createDescriptorExtension() {
        return new DescriptorSymbolTableExtension(this);
    }

    public final IrScript declareScript(IdSignature signature, Function0<? extends IrScriptSymbol> symbolFactory, Function1<? super IrScriptSymbol, ? extends IrScript> scriptFactory) {
        IrScript irScriptInvoke;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbolFactory, "symbolFactory");
        Intrinsics.checkNotNullParameter(scriptFactory, "scriptFactory");
        SymbolTableSlice.Flat<IdSignature, IrScript, IrScriptSymbol> flat = this.scriptSlice;
        synchronized (flat.getLock()) {
            IrScriptSymbol irScriptSymbol = flat.get(signature);
            if (irScriptSymbol == null) {
                IrScriptSymbol irScriptSymbolInvoke = symbolFactory.invoke();
                flat.set(signature, irScriptSymbolInvoke);
                irScriptSymbol = irScriptSymbolInvoke;
            } else {
                flat.getUnboundSymbols().remove(irScriptSymbol);
            }
            irScriptInvoke = scriptFactory.invoke(irScriptSymbol);
            if (!irScriptSymbol.isBound()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (irScriptSymbol.getOwner() != irScriptInvoke) {
                throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irScriptSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irScriptInvoke, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
        return irScriptInvoke;
    }

    public final IrClass declareClass(IdSignature signature, Function0<? extends IrClassSymbol> symbolFactory, Function1<? super IrClassSymbol, ? extends IrClass> classFactory) {
        IrClass irClassInvoke;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbolFactory, "symbolFactory");
        Intrinsics.checkNotNullParameter(classFactory, "classFactory");
        SymbolTableSlice.Flat<IdSignature, IrClass, IrClassSymbol> flat = this.classSlice;
        synchronized (flat.getLock()) {
            IrClassSymbol irClassSymbol = flat.get(signature);
            if (irClassSymbol == null) {
                IrClassSymbol irClassSymbolInvoke = symbolFactory.invoke();
                flat.set(signature, irClassSymbolInvoke);
                irClassSymbol = irClassSymbolInvoke;
            } else {
                flat.getUnboundSymbols().remove(irClassSymbol);
            }
            irClassInvoke = classFactory.invoke(irClassSymbol);
            if (!irClassSymbol.isBound()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (irClassSymbol.getOwner() != irClassInvoke) {
                throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irClassSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irClassInvoke, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
        return irClassInvoke;
    }

    public final void declareClassWithSignature(IdSignature signature, IrClassSymbol symbol) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.classSlice.set(signature, symbol);
    }

    public final IrClass declareClassIfNotExists(IdSignature signature, Function0<? extends IrClassSymbol> symbolFactory, Function1<? super IrClassSymbol, ? extends IrClass> classFactory) {
        IrClass owner;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbolFactory, "symbolFactory");
        Intrinsics.checkNotNullParameter(classFactory, "classFactory");
        SymbolTableSlice.Flat<IdSignature, IrClass, IrClassSymbol> flat = this.classSlice;
        synchronized (flat.getLock()) {
            IrClassSymbol irClassSymbolInvoke = flat.get(signature);
            if (irClassSymbolInvoke == null) {
                irClassSymbolInvoke = symbolFactory.invoke();
                flat.set(signature, irClassSymbolInvoke);
            } else if (irClassSymbolInvoke.isBound()) {
                flat.getUnboundSymbols().remove(irClassSymbolInvoke);
                owner = irClassSymbolInvoke.getOwner();
            }
            owner = classFactory.invoke(irClassSymbolInvoke);
            if (!irClassSymbolInvoke.isBound()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (irClassSymbolInvoke.getOwner() != owner) {
                throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irClassSymbolInvoke.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
        return (IrClass) owner;
    }

    public final IrClassSymbol referenceClass(IdSignature signature, Function0<? extends IrClassSymbol> symbolFactory, Function1<? super IrClassSymbol, ? extends IrClass> classFactory) {
        IrClassSymbol symbol;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbolFactory, "symbolFactory");
        Intrinsics.checkNotNullParameter(classFactory, "classFactory");
        SymbolTableSlice.Flat<IdSignature, IrClass, IrClassSymbol> flat = this.classSlice;
        synchronized (flat.getLock()) {
            symbol = flat.get(signature);
            if (symbol == null) {
                symbol = declareClass(signature, symbolFactory, classFactory).getSymbol();
                flat.getUnboundSymbols().add(symbol);
                flat.set(signature, symbol);
            }
        }
        return symbol;
    }

    @SymbolTableInternals
    public final IrClassSymbol referenceClassImpl$ir_tree(IdSignature signature, Function0<? extends IrClassSymbol> publicSymbolFactory, Function0<? extends IrClassSymbol> privateSymbolFactory) {
        Object obj;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(publicSymbolFactory, "publicSymbolFactory");
        Intrinsics.checkNotNullParameter(privateSymbolFactory, "privateSymbolFactory");
        if (signature.isPubliclyVisible()) {
            SymbolTableSlice.Flat flat = this.classSlice;
            synchronized (flat.getLock()) {
                try {
                    obj = flat.get(signature);
                    if (obj == null) {
                        obj = (IrBindableSymbol) ((IrClassSymbol) publicSymbolFactory.invoke());
                        flat.getUnboundSymbols().add(obj);
                        flat.set(signature, obj);
                    }
                    InlineMarker.finallyStart(2);
                } finally {
                    int i = 1;
                    InlineMarker.finallyStart(i);
                    InlineMarker.finallyEnd(i);
                }
            }
            return (IrClassSymbol) obj;
        }
        return privateSymbolFactory.invoke();
    }

    public final IrConstructor declareConstructor(IdSignature signature, Function0<? extends IrConstructorSymbol> symbolFactory, Function1<? super IrConstructorSymbol, ? extends IrConstructor> constructorFactory) {
        IrConstructor irConstructorInvoke;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbolFactory, "symbolFactory");
        Intrinsics.checkNotNullParameter(constructorFactory, "constructorFactory");
        SymbolTableSlice.Flat<IdSignature, IrConstructor, IrConstructorSymbol> flat = this.constructorSlice;
        synchronized (flat.getLock()) {
            IrConstructorSymbol irConstructorSymbol = flat.get(signature);
            if (irConstructorSymbol == null) {
                IrConstructorSymbol irConstructorSymbolInvoke = symbolFactory.invoke();
                flat.set(signature, irConstructorSymbolInvoke);
                irConstructorSymbol = irConstructorSymbolInvoke;
            } else {
                flat.getUnboundSymbols().remove(irConstructorSymbol);
            }
            irConstructorInvoke = constructorFactory.invoke(irConstructorSymbol);
            if (!irConstructorSymbol.isBound()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (irConstructorSymbol.getOwner() != irConstructorInvoke) {
                throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irConstructorSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irConstructorInvoke, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
        return irConstructorInvoke;
    }

    public final IrConstructor declareConstructorIfNotExists(IdSignature signature, Function0<? extends IrConstructorSymbol> symbolFactory, Function1<? super IrConstructorSymbol, ? extends IrConstructor> constructorFactory) {
        IrConstructor owner;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbolFactory, "symbolFactory");
        Intrinsics.checkNotNullParameter(constructorFactory, "constructorFactory");
        SymbolTableSlice.Flat<IdSignature, IrConstructor, IrConstructorSymbol> flat = this.constructorSlice;
        synchronized (flat.getLock()) {
            IrConstructorSymbol irConstructorSymbolInvoke = flat.get(signature);
            if (irConstructorSymbolInvoke == null) {
                irConstructorSymbolInvoke = symbolFactory.invoke();
                flat.set(signature, irConstructorSymbolInvoke);
            } else if (irConstructorSymbolInvoke.isBound()) {
                flat.getUnboundSymbols().remove(irConstructorSymbolInvoke);
                owner = irConstructorSymbolInvoke.getOwner();
            }
            owner = constructorFactory.invoke(irConstructorSymbolInvoke);
            if (!irConstructorSymbolInvoke.isBound()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (irConstructorSymbolInvoke.getOwner() != owner) {
                throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irConstructorSymbolInvoke.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
        return (IrConstructor) owner;
    }

    public final void declareConstructorWithSignature(IdSignature signature, IrConstructorSymbol symbol) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.constructorSlice.set(signature, symbol);
    }

    public final IrConstructorSymbol referenceConstructorIfAny(IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return (IrConstructorSymbol) this.constructorSlice.get(signature);
    }

    @SymbolTableInternals
    public final IrConstructorSymbol referenceConstructorImpl$ir_tree(IdSignature signature, Function0<? extends IrConstructorSymbol> publicSymbolFactory, Function0<? extends IrConstructorSymbol> privateSymbolFactory) {
        Object obj;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(publicSymbolFactory, "publicSymbolFactory");
        Intrinsics.checkNotNullParameter(privateSymbolFactory, "privateSymbolFactory");
        if (signature.isPubliclyVisible()) {
            SymbolTableSlice.Flat flat = this.constructorSlice;
            synchronized (flat.getLock()) {
                try {
                    obj = flat.get(signature);
                    if (obj == null) {
                        obj = (IrBindableSymbol) ((IrConstructorSymbol) publicSymbolFactory.invoke());
                        flat.getUnboundSymbols().add(obj);
                        flat.set(signature, obj);
                    }
                    InlineMarker.finallyStart(2);
                } finally {
                    int i = 1;
                    InlineMarker.finallyStart(i);
                    InlineMarker.finallyEnd(i);
                }
            }
            return (IrConstructorSymbol) obj;
        }
        IrConstructorSymbol irConstructorSymbolInvoke = privateSymbolFactory.invoke();
        irConstructorSymbolInvoke.setPrivateSignature(signature);
        return irConstructorSymbolInvoke;
    }

    public final IrEnumEntry declareEnumEntry(IdSignature signature, Function0<? extends IrEnumEntrySymbol> symbolFactory, Function1<? super IrEnumEntrySymbol, ? extends IrEnumEntry> enumEntryFactory) {
        IrEnumEntry irEnumEntryInvoke;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbolFactory, "symbolFactory");
        Intrinsics.checkNotNullParameter(enumEntryFactory, "enumEntryFactory");
        SymbolTableSlice.Flat<IdSignature, IrEnumEntry, IrEnumEntrySymbol> flat = this.enumEntrySlice;
        synchronized (flat.getLock()) {
            IrEnumEntrySymbol irEnumEntrySymbol = flat.get(signature);
            if (irEnumEntrySymbol == null) {
                IrEnumEntrySymbol irEnumEntrySymbolInvoke = symbolFactory.invoke();
                flat.set(signature, irEnumEntrySymbolInvoke);
                irEnumEntrySymbol = irEnumEntrySymbolInvoke;
            } else {
                flat.getUnboundSymbols().remove(irEnumEntrySymbol);
            }
            irEnumEntryInvoke = enumEntryFactory.invoke(irEnumEntrySymbol);
            if (!irEnumEntrySymbol.isBound()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (irEnumEntrySymbol.getOwner() != irEnumEntryInvoke) {
                throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irEnumEntrySymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irEnumEntryInvoke, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
        return irEnumEntryInvoke;
    }

    public final IrEnumEntry declareEnumEntryIfNotExists(IdSignature signature, Function0<? extends IrEnumEntrySymbol> symbolFactory, Function1<? super IrEnumEntrySymbol, ? extends IrEnumEntry> classFactory) {
        IrEnumEntry owner;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbolFactory, "symbolFactory");
        Intrinsics.checkNotNullParameter(classFactory, "classFactory");
        SymbolTableSlice.Flat<IdSignature, IrEnumEntry, IrEnumEntrySymbol> flat = this.enumEntrySlice;
        synchronized (flat.getLock()) {
            IrEnumEntrySymbol irEnumEntrySymbolInvoke = flat.get(signature);
            if (irEnumEntrySymbolInvoke == null) {
                irEnumEntrySymbolInvoke = symbolFactory.invoke();
                flat.set(signature, irEnumEntrySymbolInvoke);
            } else if (irEnumEntrySymbolInvoke.isBound()) {
                flat.getUnboundSymbols().remove(irEnumEntrySymbolInvoke);
                owner = irEnumEntrySymbolInvoke.getOwner();
            }
            owner = classFactory.invoke(irEnumEntrySymbolInvoke);
            if (!irEnumEntrySymbolInvoke.isBound()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (irEnumEntrySymbolInvoke.getOwner() != owner) {
                throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irEnumEntrySymbolInvoke.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
        return (IrEnumEntry) owner;
    }

    @SymbolTableInternals
    public final IrEnumEntrySymbol referenceEnumEntryImpl$ir_tree(IdSignature signature, Function0<? extends IrEnumEntrySymbol> publicSymbolFactory, Function0<? extends IrEnumEntrySymbol> privateSymbolFactory) {
        Object obj;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(publicSymbolFactory, "publicSymbolFactory");
        Intrinsics.checkNotNullParameter(privateSymbolFactory, "privateSymbolFactory");
        if (signature.isPubliclyVisible()) {
            SymbolTableSlice.Flat flat = this.enumEntrySlice;
            synchronized (flat.getLock()) {
                try {
                    obj = flat.get(signature);
                    if (obj == null) {
                        obj = (IrBindableSymbol) ((IrEnumEntrySymbol) publicSymbolFactory.invoke());
                        flat.getUnboundSymbols().add(obj);
                        flat.set(signature, obj);
                    }
                    InlineMarker.finallyStart(2);
                } finally {
                    int i = 1;
                    InlineMarker.finallyStart(i);
                    InlineMarker.finallyEnd(i);
                }
            }
            return (IrEnumEntrySymbol) obj;
        }
        IrEnumEntrySymbol irEnumEntrySymbolInvoke = privateSymbolFactory.invoke();
        irEnumEntrySymbolInvoke.setPrivateSignature(signature);
        return irEnumEntrySymbolInvoke;
    }

    public final IrField declareField(IdSignature signature, Function0<? extends IrFieldSymbol> symbolFactory, Function1<? super IrFieldSymbol, ? extends IrField> propertyFactory) {
        IrField irFieldInvoke;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbolFactory, "symbolFactory");
        Intrinsics.checkNotNullParameter(propertyFactory, "propertyFactory");
        SymbolTableSlice.Flat<IdSignature, IrField, IrFieldSymbol> flat = this.fieldSlice;
        synchronized (flat.getLock()) {
            IrFieldSymbol irFieldSymbol = flat.get(signature);
            if (irFieldSymbol == null) {
                IrFieldSymbol irFieldSymbolInvoke = symbolFactory.invoke();
                flat.set(signature, irFieldSymbolInvoke);
                irFieldSymbol = irFieldSymbolInvoke;
            } else {
                flat.getUnboundSymbols().remove(irFieldSymbol);
            }
            irFieldInvoke = propertyFactory.invoke(irFieldSymbol);
            if (!irFieldSymbol.isBound()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (irFieldSymbol.getOwner() != irFieldInvoke) {
                throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irFieldSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irFieldInvoke, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
        return irFieldInvoke;
    }

    public final void declareFieldWithSignature(IdSignature signature, IrFieldSymbol symbol) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.fieldSlice.set(signature, symbol);
    }

    @SymbolTableInternals
    public final IrFieldSymbol referenceFieldImpl$ir_tree(IdSignature signature, Function0<? extends IrFieldSymbol> publicSymbolFactory, Function0<? extends IrFieldSymbol> privateSymbolFactory) {
        Object obj;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(publicSymbolFactory, "publicSymbolFactory");
        Intrinsics.checkNotNullParameter(privateSymbolFactory, "privateSymbolFactory");
        if (signature.isPubliclyVisible()) {
            SymbolTableSlice.Flat flat = this.fieldSlice;
            synchronized (flat.getLock()) {
                try {
                    obj = flat.get(signature);
                    if (obj == null) {
                        obj = (IrBindableSymbol) ((IrFieldSymbol) publicSymbolFactory.invoke());
                        flat.getUnboundSymbols().add(obj);
                        flat.set(signature, obj);
                    }
                    InlineMarker.finallyStart(2);
                } finally {
                    int i = 1;
                    InlineMarker.finallyStart(i);
                    InlineMarker.finallyEnd(i);
                }
            }
            return (IrFieldSymbol) obj;
        }
        IrFieldSymbol irFieldSymbolInvoke = privateSymbolFactory.invoke();
        irFieldSymbolInvoke.setPrivateSignature(signature);
        return irFieldSymbolInvoke;
    }

    public final IrProperty declareProperty(IdSignature signature, Function0<? extends IrPropertySymbol> symbolFactory, Function1<? super IrPropertySymbol, ? extends IrProperty> propertyFactory) {
        IrProperty irPropertyInvoke;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbolFactory, "symbolFactory");
        Intrinsics.checkNotNullParameter(propertyFactory, "propertyFactory");
        SymbolTableSlice.Flat<IdSignature, IrProperty, IrPropertySymbol> flat = this.propertySlice;
        synchronized (flat.getLock()) {
            IrPropertySymbol irPropertySymbol = flat.get(signature);
            if (irPropertySymbol == null) {
                IrPropertySymbol irPropertySymbolInvoke = symbolFactory.invoke();
                flat.set(signature, irPropertySymbolInvoke);
                irPropertySymbol = irPropertySymbolInvoke;
            } else {
                flat.getUnboundSymbols().remove(irPropertySymbol);
            }
            irPropertyInvoke = propertyFactory.invoke(irPropertySymbol);
            if (!irPropertySymbol.isBound()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (irPropertySymbol.getOwner() != irPropertyInvoke) {
                throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irPropertySymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irPropertyInvoke, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
        return irPropertyInvoke;
    }

    public final IrProperty declarePropertyIfNotExists(IdSignature signature, Function0<? extends IrPropertySymbol> symbolFactory, Function1<? super IrPropertySymbol, ? extends IrProperty> propertyFactory) {
        IrProperty owner;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbolFactory, "symbolFactory");
        Intrinsics.checkNotNullParameter(propertyFactory, "propertyFactory");
        SymbolTableSlice.Flat<IdSignature, IrProperty, IrPropertySymbol> flat = this.propertySlice;
        synchronized (flat.getLock()) {
            IrPropertySymbol irPropertySymbolInvoke = flat.get(signature);
            if (irPropertySymbolInvoke == null) {
                irPropertySymbolInvoke = symbolFactory.invoke();
                flat.set(signature, irPropertySymbolInvoke);
            } else if (irPropertySymbolInvoke.isBound()) {
                flat.getUnboundSymbols().remove(irPropertySymbolInvoke);
                owner = irPropertySymbolInvoke.getOwner();
            }
            owner = propertyFactory.invoke(irPropertySymbolInvoke);
            if (!irPropertySymbolInvoke.isBound()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (irPropertySymbolInvoke.getOwner() != owner) {
                throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irPropertySymbolInvoke.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
        return (IrProperty) owner;
    }

    public final void declarePropertyWithSignature(IdSignature signature, IrPropertySymbol symbol) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.propertySlice.set(signature, symbol);
    }

    public final IrPropertySymbol referencePropertyIfAny(IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return (IrPropertySymbol) this.propertySlice.get(signature);
    }

    @SymbolTableInternals
    public final IrPropertySymbol referencePropertyImpl$ir_tree(IdSignature signature, Function0<? extends IrPropertySymbol> publicSymbolFactory, Function0<? extends IrPropertySymbol> privateSymbolFactory) {
        Object obj;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(publicSymbolFactory, "publicSymbolFactory");
        Intrinsics.checkNotNullParameter(privateSymbolFactory, "privateSymbolFactory");
        if (signature.isPubliclyVisible()) {
            SymbolTableSlice.Flat flat = this.propertySlice;
            synchronized (flat.getLock()) {
                try {
                    obj = flat.get(signature);
                    if (obj == null) {
                        obj = (IrBindableSymbol) ((IrPropertySymbol) publicSymbolFactory.invoke());
                        flat.getUnboundSymbols().add(obj);
                        flat.set(signature, obj);
                    }
                    InlineMarker.finallyStart(2);
                } finally {
                    int i = 1;
                    InlineMarker.finallyStart(i);
                    InlineMarker.finallyEnd(i);
                }
            }
            return (IrPropertySymbol) obj;
        }
        IrPropertySymbol irPropertySymbolInvoke = privateSymbolFactory.invoke();
        irPropertySymbolInvoke.setPrivateSignature(signature);
        return irPropertySymbolInvoke;
    }

    public final IrTypeAlias declareTypeAlias(IdSignature signature, Function0<? extends IrTypeAliasSymbol> symbolFactory, Function1<? super IrTypeAliasSymbol, ? extends IrTypeAlias> factory) {
        IrTypeAlias irTypeAliasInvoke;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbolFactory, "symbolFactory");
        Intrinsics.checkNotNullParameter(factory, "factory");
        SymbolTableSlice.Flat<IdSignature, IrTypeAlias, IrTypeAliasSymbol> flat = this.typeAliasSlice;
        synchronized (flat.getLock()) {
            IrTypeAliasSymbol irTypeAliasSymbol = flat.get(signature);
            if (irTypeAliasSymbol == null) {
                IrTypeAliasSymbol irTypeAliasSymbolInvoke = symbolFactory.invoke();
                flat.set(signature, irTypeAliasSymbolInvoke);
                irTypeAliasSymbol = irTypeAliasSymbolInvoke;
            } else {
                flat.getUnboundSymbols().remove(irTypeAliasSymbol);
            }
            irTypeAliasInvoke = factory.invoke(irTypeAliasSymbol);
            if (!irTypeAliasSymbol.isBound()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (irTypeAliasSymbol.getOwner() != irTypeAliasInvoke) {
                throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irTypeAliasSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irTypeAliasInvoke, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
        return irTypeAliasInvoke;
    }

    public final IrTypeAlias declareTypeAliasIfNotExists(IdSignature signature, Function0<? extends IrTypeAliasSymbol> symbolFactory, Function1<? super IrTypeAliasSymbol, ? extends IrTypeAlias> typeAliasFactory) {
        IrTypeAlias owner;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbolFactory, "symbolFactory");
        Intrinsics.checkNotNullParameter(typeAliasFactory, "typeAliasFactory");
        SymbolTableSlice.Flat<IdSignature, IrTypeAlias, IrTypeAliasSymbol> flat = this.typeAliasSlice;
        synchronized (flat.getLock()) {
            IrTypeAliasSymbol irTypeAliasSymbolInvoke = flat.get(signature);
            if (irTypeAliasSymbolInvoke == null) {
                irTypeAliasSymbolInvoke = symbolFactory.invoke();
                flat.set(signature, irTypeAliasSymbolInvoke);
            } else if (irTypeAliasSymbolInvoke.isBound()) {
                flat.getUnboundSymbols().remove(irTypeAliasSymbolInvoke);
                owner = irTypeAliasSymbolInvoke.getOwner();
            }
            owner = typeAliasFactory.invoke(irTypeAliasSymbolInvoke);
            if (!irTypeAliasSymbolInvoke.isBound()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (irTypeAliasSymbolInvoke.getOwner() != owner) {
                throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irTypeAliasSymbolInvoke.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
        return (IrTypeAlias) owner;
    }

    @SymbolTableInternals
    public final IrTypeAliasSymbol referenceTypeAliasImpl$ir_tree(IdSignature signature, Function0<? extends IrTypeAliasSymbol> publicSymbolFactory, Function0<? extends IrTypeAliasSymbol> privateSymbolFactory) {
        Object obj;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(publicSymbolFactory, "publicSymbolFactory");
        Intrinsics.checkNotNullParameter(privateSymbolFactory, "privateSymbolFactory");
        if (signature.isPubliclyVisible()) {
            SymbolTableSlice.Flat flat = this.typeAliasSlice;
            synchronized (flat.getLock()) {
                try {
                    obj = flat.get(signature);
                    if (obj == null) {
                        obj = (IrBindableSymbol) ((IrTypeAliasSymbol) publicSymbolFactory.invoke());
                        flat.getUnboundSymbols().add(obj);
                        flat.set(signature, obj);
                    }
                    InlineMarker.finallyStart(2);
                } finally {
                    int i = 1;
                    InlineMarker.finallyStart(i);
                    InlineMarker.finallyEnd(i);
                }
            }
            return (IrTypeAliasSymbol) obj;
        }
        IrTypeAliasSymbol irTypeAliasSymbolInvoke = privateSymbolFactory.invoke();
        irTypeAliasSymbolInvoke.setPrivateSignature(signature);
        return irTypeAliasSymbolInvoke;
    }

    public final IrSimpleFunction declareSimpleFunction(IdSignature signature, Function0<? extends IrSimpleFunctionSymbol> symbolFactory, Function1<? super IrSimpleFunctionSymbol, ? extends IrSimpleFunction> functionFactory) {
        IrSimpleFunction irSimpleFunctionInvoke;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbolFactory, "symbolFactory");
        Intrinsics.checkNotNullParameter(functionFactory, "functionFactory");
        SymbolTableSlice.Flat<IdSignature, IrSimpleFunction, IrSimpleFunctionSymbol> flat = this.functionSlice;
        synchronized (flat.getLock()) {
            IrSimpleFunctionSymbol irSimpleFunctionSymbol = flat.get(signature);
            if (irSimpleFunctionSymbol == null) {
                IrSimpleFunctionSymbol irSimpleFunctionSymbolInvoke = symbolFactory.invoke();
                flat.set(signature, irSimpleFunctionSymbolInvoke);
                irSimpleFunctionSymbol = irSimpleFunctionSymbolInvoke;
            } else {
                flat.getUnboundSymbols().remove(irSimpleFunctionSymbol);
            }
            irSimpleFunctionInvoke = functionFactory.invoke(irSimpleFunctionSymbol);
            if (!irSimpleFunctionSymbol.isBound()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (irSimpleFunctionSymbol.getOwner() != irSimpleFunctionInvoke) {
                throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irSimpleFunctionSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irSimpleFunctionInvoke, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
        return irSimpleFunctionInvoke;
    }

    public final IrSimpleFunction declareSimpleFunctionIfNotExists(IdSignature signature, Function0<? extends IrSimpleFunctionSymbol> symbolFactory, Function1<? super IrSimpleFunctionSymbol, ? extends IrSimpleFunction> functionFactory) {
        IrSimpleFunction owner;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbolFactory, "symbolFactory");
        Intrinsics.checkNotNullParameter(functionFactory, "functionFactory");
        SymbolTableSlice.Flat<IdSignature, IrSimpleFunction, IrSimpleFunctionSymbol> flat = this.functionSlice;
        synchronized (flat.getLock()) {
            IrSimpleFunctionSymbol irSimpleFunctionSymbolInvoke = flat.get(signature);
            if (irSimpleFunctionSymbolInvoke == null) {
                irSimpleFunctionSymbolInvoke = symbolFactory.invoke();
                flat.set(signature, irSimpleFunctionSymbolInvoke);
            } else if (irSimpleFunctionSymbolInvoke.isBound()) {
                flat.getUnboundSymbols().remove(irSimpleFunctionSymbolInvoke);
                owner = irSimpleFunctionSymbolInvoke.getOwner();
            }
            owner = functionFactory.invoke(irSimpleFunctionSymbolInvoke);
            if (!irSimpleFunctionSymbolInvoke.isBound()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (irSimpleFunctionSymbolInvoke.getOwner() != owner) {
                throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irSimpleFunctionSymbolInvoke.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
        return (IrSimpleFunction) owner;
    }

    public final void declareSimpleFunctionWithSignature(IdSignature signature, IrSimpleFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.functionSlice.set(signature, symbol);
    }

    public final IrSimpleFunctionSymbol referenceSimpleFunctionIfAny(IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return (IrSimpleFunctionSymbol) this.functionSlice.get(signature);
    }

    @SymbolTableInternals
    public final IrSimpleFunctionSymbol referenceSimpleFunctionImpl$ir_tree(IdSignature signature, Function0<? extends IrSimpleFunctionSymbol> publicSymbolFactory, Function0<? extends IrSimpleFunctionSymbol> privateSymbolFactory) {
        Object obj;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(publicSymbolFactory, "publicSymbolFactory");
        Intrinsics.checkNotNullParameter(privateSymbolFactory, "privateSymbolFactory");
        if (signature.isPubliclyVisible()) {
            SymbolTableSlice.Flat flat = this.functionSlice;
            synchronized (flat.getLock()) {
                try {
                    obj = flat.get(signature);
                    if (obj == null) {
                        obj = (IrBindableSymbol) ((IrSimpleFunctionSymbol) publicSymbolFactory.invoke());
                        flat.getUnboundSymbols().add(obj);
                        flat.set(signature, obj);
                    }
                    InlineMarker.finallyStart(2);
                } finally {
                    int i = 1;
                    InlineMarker.finallyStart(i);
                    InlineMarker.finallyEnd(i);
                }
            }
            return (IrSimpleFunctionSymbol) obj;
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbolInvoke = privateSymbolFactory.invoke();
        irSimpleFunctionSymbolInvoke.setPrivateSignature(signature);
        return irSimpleFunctionSymbolInvoke;
    }

    public final IrTypeParameter declareGlobalTypeParameter(IdSignature signature, Function0<? extends IrTypeParameterSymbol> symbolFactory, Function1<? super IrTypeParameterSymbol, ? extends IrTypeParameter> typeParameterFactory) {
        IrTypeParameter irTypeParameterInvoke;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbolFactory, "symbolFactory");
        Intrinsics.checkNotNullParameter(typeParameterFactory, "typeParameterFactory");
        SymbolTableSlice.Flat<IdSignature, IrTypeParameter, IrTypeParameterSymbol> flat = this.globalTypeParameterSlice;
        synchronized (flat.getLock()) {
            IrTypeParameterSymbol irTypeParameterSymbol = flat.get(signature);
            if (irTypeParameterSymbol == null) {
                IrTypeParameterSymbol irTypeParameterSymbolInvoke = symbolFactory.invoke();
                flat.set(signature, irTypeParameterSymbolInvoke);
                irTypeParameterSymbol = irTypeParameterSymbolInvoke;
            } else {
                flat.getUnboundSymbols().remove(irTypeParameterSymbol);
            }
            irTypeParameterInvoke = typeParameterFactory.invoke(irTypeParameterSymbol);
            if (!irTypeParameterSymbol.isBound()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (irTypeParameterSymbol.getOwner() != irTypeParameterInvoke) {
                throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irTypeParameterSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irTypeParameterInvoke, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
        return irTypeParameterInvoke;
    }

    public final IrTypeParameter declareScopedTypeParameter(IdSignature signature, Function1<? super IdSignature, ? extends IrTypeParameterSymbol> symbolFactory, Function1<? super IrTypeParameterSymbol, ? extends IrTypeParameter> typeParameterFactory) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(symbolFactory, "symbolFactory");
        Intrinsics.checkNotNullParameter(typeParameterFactory, "typeParameterFactory");
        return typeParameterFactory.invoke(symbolFactory.invoke(signature));
    }

    @SymbolTableInternals
    public final IrTypeParameterSymbol referenceTypeParameterImpl$ir_tree(IdSignature signature, Function0<? extends IrTypeParameterSymbol> publicSymbolFactory, Function0<? extends IrTypeParameterSymbol> privateSymbolFactory) {
        Object obj;
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(publicSymbolFactory, "publicSymbolFactory");
        Intrinsics.checkNotNullParameter(privateSymbolFactory, "privateSymbolFactory");
        if (signature.isPubliclyVisible()) {
            SymbolTableSlice.Flat flat = this.globalTypeParameterSlice;
            synchronized (flat.getLock()) {
                try {
                    obj = flat.get(signature);
                    if (obj == null) {
                        obj = (IrBindableSymbol) ((IrTypeParameterSymbol) publicSymbolFactory.invoke());
                        flat.getUnboundSymbols().add(obj);
                        flat.set(signature, obj);
                    }
                    InlineMarker.finallyStart(2);
                } finally {
                    int i = 1;
                    InlineMarker.finallyStart(i);
                    InlineMarker.finallyEnd(i);
                }
            }
            return (IrTypeParameterSymbol) obj;
        }
        IrTypeParameterSymbol irTypeParameterSymbolInvoke = privateSymbolFactory.invoke();
        irTypeParameterSymbolInvoke.setPrivateSignature(signature);
        return irTypeParameterSymbolInvoke;
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public void enterScope(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        getDescriptorExtension().enterScope(symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public void enterScope(IrDeclaration owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        getDescriptorExtension().enterScope(owner);
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public void leaveScope(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        getDescriptorExtension().leaveScope(symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public void leaveScope(IrDeclaration owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        getDescriptorExtension().leaveScope(owner);
    }

    @DelicateSymbolTableApi
    public final void forEachDeclarationSymbol(final Function1<? super IrSymbol, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        this.classSlice.forEachSymbol$ir_tree(new Function1<IrSymbol, Unit>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTable.forEachDeclarationSymbol.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrSymbol irSymbol) {
                invoke2(irSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                block.invoke(it);
            }
        });
        this.constructorSlice.forEachSymbol$ir_tree(new Function1<IrSymbol, Unit>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTable.forEachDeclarationSymbol.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrSymbol irSymbol) {
                invoke2(irSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                block.invoke(it);
            }
        });
        this.functionSlice.forEachSymbol$ir_tree(new Function1<IrSymbol, Unit>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTable.forEachDeclarationSymbol.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrSymbol irSymbol) {
                invoke2(irSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                block.invoke(it);
            }
        });
        this.propertySlice.forEachSymbol$ir_tree(new Function1<IrSymbol, Unit>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTable.forEachDeclarationSymbol.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrSymbol irSymbol) {
                invoke2(irSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                block.invoke(it);
            }
        });
        this.enumEntrySlice.forEachSymbol$ir_tree(new Function1<IrSymbol, Unit>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTable.forEachDeclarationSymbol.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrSymbol irSymbol) {
                invoke2(irSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                block.invoke(it);
            }
        });
        this.typeAliasSlice.forEachSymbol$ir_tree(new Function1<IrSymbol, Unit>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTable.forEachDeclarationSymbol.6
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrSymbol irSymbol) {
                invoke2(irSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                block.invoke(it);
            }
        });
        this.fieldSlice.forEachSymbol$ir_tree(new Function1<IrSymbol, Unit>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTable.forEachDeclarationSymbol.7
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrSymbol irSymbol) {
                invoke2(irSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                block.invoke(it);
            }
        });
    }

    public final Set<IrSymbol> getAllUnboundSymbols() {
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        _get_allUnboundSymbols_$lambda$35$addUnbound(setCreateSetBuilder, this.classSlice);
        _get_allUnboundSymbols_$lambda$35$addUnbound(setCreateSetBuilder, this.constructorSlice);
        _get_allUnboundSymbols_$lambda$35$addUnbound(setCreateSetBuilder, this.functionSlice);
        _get_allUnboundSymbols_$lambda$35$addUnbound(setCreateSetBuilder, this.propertySlice);
        _get_allUnboundSymbols_$lambda$35$addUnbound(setCreateSetBuilder, this.enumEntrySlice);
        _get_allUnboundSymbols_$lambda$35$addUnbound(setCreateSetBuilder, this.typeAliasSlice);
        _get_allUnboundSymbols_$lambda$35$addUnbound(setCreateSetBuilder, this.fieldSlice);
        return SetsKt.build(setCreateSetBuilder);
    }

    private static final void _get_allUnboundSymbols_$lambda$35$addUnbound(Set<IrSymbol> set, SymbolTableSlice<IdSignature, ?, ?> symbolTableSlice) {
        for (Object obj : symbolTableSlice.getUnboundSymbols()) {
            if (!((IrBindableSymbol) obj).isBound()) {
                set.add(obj);
            }
        }
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public IrClassSymbol referenceClass(IdSignature signature) {
        Object obj;
        Intrinsics.checkNotNullParameter(signature, "signature");
        ClassDescriptor classDescriptor = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        if (signature.isPubliclyVisible()) {
            SymbolTableSlice.Flat flat = this.classSlice;
            synchronized (flat.getLock()) {
                Object obj2 = flat.get(signature);
                obj = obj2;
                if (obj2 == null) {
                    IrClassPublicSymbolImpl irClassPublicSymbolImpl = new IrClassPublicSymbolImpl(signature, classDescriptor, 2, z3 ? 1 : 0);
                    flat.getUnboundSymbols().add(irClassPublicSymbolImpl);
                    flat.set(signature, irClassPublicSymbolImpl);
                    obj = irClassPublicSymbolImpl;
                }
            }
            return (IrClassSymbol) obj;
        }
        IrClassSymbolImpl irClassSymbolImpl = new IrClassSymbolImpl(z2 ? 1 : 0, 1, z ? 1 : 0);
        irClassSymbolImpl.setPrivateSignature(signature);
        return irClassSymbolImpl;
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public IrConstructorSymbol referenceConstructor(IdSignature signature) {
        Object obj;
        Intrinsics.checkNotNullParameter(signature, "signature");
        ClassConstructorDescriptor classConstructorDescriptor = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        if (signature.isPubliclyVisible()) {
            SymbolTableSlice.Flat flat = this.constructorSlice;
            synchronized (flat.getLock()) {
                Object obj2 = flat.get(signature);
                obj = obj2;
                if (obj2 == null) {
                    IrConstructorPublicSymbolImpl irConstructorPublicSymbolImpl = new IrConstructorPublicSymbolImpl(signature, classConstructorDescriptor, 2, z3 ? 1 : 0);
                    flat.getUnboundSymbols().add(irConstructorPublicSymbolImpl);
                    flat.set(signature, irConstructorPublicSymbolImpl);
                    obj = irConstructorPublicSymbolImpl;
                }
            }
            return (IrConstructorSymbol) obj;
        }
        IrConstructorSymbolImpl irConstructorSymbolImpl = new IrConstructorSymbolImpl(z2 ? 1 : 0, 1, z ? 1 : 0);
        irConstructorSymbolImpl.setPrivateSignature(signature);
        return irConstructorSymbolImpl;
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public IrEnumEntrySymbol referenceEnumEntry(IdSignature signature) {
        Object obj;
        Intrinsics.checkNotNullParameter(signature, "signature");
        ClassDescriptor classDescriptor = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        if (signature.isPubliclyVisible()) {
            SymbolTableSlice.Flat flat = this.enumEntrySlice;
            synchronized (flat.getLock()) {
                Object obj2 = flat.get(signature);
                obj = obj2;
                if (obj2 == null) {
                    IrEnumEntryPublicSymbolImpl irEnumEntryPublicSymbolImpl = new IrEnumEntryPublicSymbolImpl(signature, classDescriptor, 2, z3 ? 1 : 0);
                    flat.getUnboundSymbols().add(irEnumEntryPublicSymbolImpl);
                    flat.set(signature, irEnumEntryPublicSymbolImpl);
                    obj = irEnumEntryPublicSymbolImpl;
                }
            }
            return (IrEnumEntrySymbol) obj;
        }
        IrEnumEntrySymbolImpl irEnumEntrySymbolImpl = new IrEnumEntrySymbolImpl(z2 ? 1 : 0, 1, z ? 1 : 0);
        irEnumEntrySymbolImpl.setPrivateSignature(signature);
        return irEnumEntrySymbolImpl;
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public IrFieldSymbol referenceField(IdSignature signature) {
        Object obj;
        Intrinsics.checkNotNullParameter(signature, "signature");
        PropertyDescriptor propertyDescriptor = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        if (signature.isPubliclyVisible()) {
            SymbolTableSlice.Flat flat = this.fieldSlice;
            synchronized (flat.getLock()) {
                Object obj2 = flat.get(signature);
                obj = obj2;
                if (obj2 == null) {
                    IrFieldPublicSymbolImpl irFieldPublicSymbolImpl = new IrFieldPublicSymbolImpl(signature, propertyDescriptor, 2, z3 ? 1 : 0);
                    flat.getUnboundSymbols().add(irFieldPublicSymbolImpl);
                    flat.set(signature, irFieldPublicSymbolImpl);
                    obj = irFieldPublicSymbolImpl;
                }
            }
            return (IrFieldSymbol) obj;
        }
        IrFieldSymbolImpl irFieldSymbolImpl = new IrFieldSymbolImpl(z2 ? 1 : 0, 1, z ? 1 : 0);
        irFieldSymbolImpl.setPrivateSignature(signature);
        return irFieldSymbolImpl;
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public IrPropertySymbol referenceProperty(IdSignature signature) {
        Object obj;
        Intrinsics.checkNotNullParameter(signature, "signature");
        PropertyDescriptor propertyDescriptor = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        if (signature.isPubliclyVisible()) {
            SymbolTableSlice.Flat flat = this.propertySlice;
            synchronized (flat.getLock()) {
                Object obj2 = flat.get(signature);
                obj = obj2;
                if (obj2 == null) {
                    IrPropertyPublicSymbolImpl irPropertyPublicSymbolImpl = new IrPropertyPublicSymbolImpl(signature, propertyDescriptor, 2, z3 ? 1 : 0);
                    flat.getUnboundSymbols().add(irPropertyPublicSymbolImpl);
                    flat.set(signature, irPropertyPublicSymbolImpl);
                    obj = irPropertyPublicSymbolImpl;
                }
            }
            return (IrPropertySymbol) obj;
        }
        IrPropertySymbolImpl irPropertySymbolImpl = new IrPropertySymbolImpl(z2 ? 1 : 0, 1, z ? 1 : 0);
        irPropertySymbolImpl.setPrivateSignature(signature);
        return irPropertySymbolImpl;
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public IrTypeAliasSymbol referenceTypeAlias(IdSignature signature) {
        Object obj;
        Intrinsics.checkNotNullParameter(signature, "signature");
        TypeAliasDescriptor typeAliasDescriptor = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        if (signature.isPubliclyVisible()) {
            SymbolTableSlice.Flat flat = this.typeAliasSlice;
            synchronized (flat.getLock()) {
                Object obj2 = flat.get(signature);
                obj = obj2;
                if (obj2 == null) {
                    IrTypeAliasPublicSymbolImpl irTypeAliasPublicSymbolImpl = new IrTypeAliasPublicSymbolImpl(signature, typeAliasDescriptor, 2, z3 ? 1 : 0);
                    flat.getUnboundSymbols().add(irTypeAliasPublicSymbolImpl);
                    flat.set(signature, irTypeAliasPublicSymbolImpl);
                    obj = irTypeAliasPublicSymbolImpl;
                }
            }
            return (IrTypeAliasSymbol) obj;
        }
        IrTypeAliasSymbolImpl irTypeAliasSymbolImpl = new IrTypeAliasSymbolImpl(z2 ? 1 : 0, 1, z ? 1 : 0);
        irTypeAliasSymbolImpl.setPrivateSignature(signature);
        return irTypeAliasSymbolImpl;
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public IrSimpleFunctionSymbol referenceSimpleFunction(IdSignature signature) {
        Object obj;
        Intrinsics.checkNotNullParameter(signature, "signature");
        FunctionDescriptor functionDescriptor = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        if (signature.isPubliclyVisible()) {
            SymbolTableSlice.Flat flat = this.functionSlice;
            synchronized (flat.getLock()) {
                Object obj2 = flat.get(signature);
                obj = obj2;
                if (obj2 == null) {
                    IrSimpleFunctionPublicSymbolImpl irSimpleFunctionPublicSymbolImpl = new IrSimpleFunctionPublicSymbolImpl(signature, functionDescriptor, 2, z3 ? 1 : 0);
                    flat.getUnboundSymbols().add(irSimpleFunctionPublicSymbolImpl);
                    flat.set(signature, irSimpleFunctionPublicSymbolImpl);
                    obj = irSimpleFunctionPublicSymbolImpl;
                }
            }
            return (IrSimpleFunctionSymbol) obj;
        }
        IrSimpleFunctionSymbolImpl irSimpleFunctionSymbolImpl = new IrSimpleFunctionSymbolImpl(z2 ? 1 : 0, 1, z ? 1 : 0);
        irSimpleFunctionSymbolImpl.setPrivateSignature(signature);
        IrSimpleFunctionSymbolImpl irSimpleFunctionSymbolImpl2 = irSimpleFunctionSymbolImpl;
        irSimpleFunctionSymbolImpl2.setPrivateSignature(signature);
        return irSimpleFunctionSymbolImpl2;
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public IrTypeParameterSymbol referenceTypeParameter(IdSignature signature) {
        Object obj;
        Intrinsics.checkNotNullParameter(signature, "signature");
        TypeParameterDescriptor typeParameterDescriptor = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        if (signature.isPubliclyVisible()) {
            SymbolTableSlice.Flat flat = this.globalTypeParameterSlice;
            synchronized (flat.getLock()) {
                Object obj2 = flat.get(signature);
                obj = obj2;
                if (obj2 == null) {
                    IrTypeParameterPublicSymbolImpl irTypeParameterPublicSymbolImpl = new IrTypeParameterPublicSymbolImpl(signature, typeParameterDescriptor, 2, z3 ? 1 : 0);
                    flat.getUnboundSymbols().add(irTypeParameterPublicSymbolImpl);
                    flat.set(signature, irTypeParameterPublicSymbolImpl);
                    obj = irTypeParameterPublicSymbolImpl;
                }
            }
            return (IrTypeParameterSymbol) obj;
        }
        IrTypeParameterSymbolImpl irTypeParameterSymbolImpl = new IrTypeParameterSymbolImpl(z2 ? 1 : 0, 1, z ? 1 : 0);
        irTypeParameterSymbolImpl.setPrivateSignature(signature);
        return irTypeParameterSymbolImpl;
    }
}
