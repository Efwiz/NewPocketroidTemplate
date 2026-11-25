package org.jetbrains.kotlin.ir.util;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
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
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
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
import org.jetbrains.kotlin.ir.symbols.impl.IrScriptSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeAliasPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeAliasSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeParameterPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeParameterSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.utils.ThreadLocalKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: SymbolTableExtension.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0098\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b4\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u001c\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0002*\u0002H\u0001*\b\b\u0002\u0010\u0003*\u0002H\u0001*\b\b\u0003\u0010\u0004*\u0002H\u0001*\b\b\u0004\u0010\u0005*\u0002H\u0001*\b\b\u0005\u0010\u0006*\u0002H\u0001*\b\b\u0006\u0010\u0007*\u0002H\u0001*\b\b\u0007\u0010\b*\u0002H\u0001*\b\b\b\u0010\t*\u0002H\u000122\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\t0\nB\r\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0017\u0010C\u001a\u0004\u0018\u00010D2\u0006\u0010E\u001a\u00028\u0001H$¢\u0006\u0002\u0010FJ\u0017\u0010G\u001a\u0004\u0018\u00010D2\u0006\u0010E\u001a\u00028\u0006H$¢\u0006\u0002\u0010FJ\u0017\u0010H\u001a\u0004\u0018\u00010D2\u0006\u0010E\u001a\u00028\u0000H$¢\u0006\u0002\u0010FJ\u001f\u0010I\u001a\u00020\u00182\u0006\u0010E\u001a\u00028\u00012\b\u0010J\u001a\u0004\u0018\u00010DH\u0004¢\u0006\u0002\u0010KJ\u001f\u0010L\u001a\u00020\u001b2\u0006\u0010E\u001a\u00028\u00052\b\u0010J\u001a\u0004\u0018\u00010DH\u0004¢\u0006\u0002\u0010MJ\u001f\u0010N\u001a\u00020\u001e2\u0006\u0010E\u001a\u00028\u00012\b\u0010J\u001a\u0004\u0018\u00010DH\u0004¢\u0006\u0002\u0010OJ\u001f\u0010P\u001a\u00020!2\u0006\u0010E\u001a\u00028\u00062\b\u0010J\u001a\u0004\u0018\u00010DH\u0004¢\u0006\u0002\u0010QJ\u001f\u0010R\u001a\u00020$2\u0006\u0010E\u001a\u00028\u00042\b\u0010J\u001a\u0004\u0018\u00010DH\u0004¢\u0006\u0002\u0010SJ\u0015\u0010T\u001a\u00020\u00182\u0006\u0010U\u001a\u00028\u0001H\u0014¢\u0006\u0002\u0010VJ\u0015\u0010W\u001a\u00020\u001b2\u0006\u0010E\u001a\u00028\u0005H\u0014¢\u0006\u0002\u0010XJ\u0015\u0010Y\u001a\u00020\u001e2\u0006\u0010E\u001a\u00028\u0001H\u0014¢\u0006\u0002\u0010ZJ\u0015\u0010[\u001a\u00020!2\u0006\u0010E\u001a\u00028\u0006H\u0014¢\u0006\u0002\u0010\\J\u0015\u0010]\u001a\u00020$2\u0006\u0010E\u001a\u00028\u0004H\u0014¢\u0006\u0002\u0010^J\u0015\u0010_\u001a\u00020.2\u0006\u0010E\u001a\u00028\u0006H\u0014¢\u0006\u0002\u0010`J\u0015\u0010a\u001a\u00020B2\u0006\u0010E\u001a\u00028\u0002H\u0014¢\u0006\u0002\u0010bJ\u0015\u0010c\u001a\u00020'2\u0006\u0010E\u001a\u00028\bH\u0014¢\u0006\u0002\u0010dJ\u001f\u0010e\u001a\u00020.2\u0006\u0010E\u001a\u00028\u00062\b\u0010J\u001a\u0004\u0018\u00010DH\u0004¢\u0006\u0002\u0010fJ\u001d\u0010g\u001a\u00020\u00182\u0006\u0010E\u001a\u00028\u00012\u0006\u0010J\u001a\u00020DH\u0014¢\u0006\u0002\u0010KJ\u001d\u0010h\u001a\u00020\u001b2\u0006\u0010E\u001a\u00028\u00052\u0006\u0010J\u001a\u00020DH\u0014¢\u0006\u0002\u0010MJ\u001d\u0010i\u001a\u00020\u001e2\u0006\u0010E\u001a\u00028\u00012\u0006\u0010J\u001a\u00020DH\u0014¢\u0006\u0002\u0010OJ\u001d\u0010j\u001a\u00020!2\u0006\u0010E\u001a\u00028\u00062\u0006\u0010J\u001a\u00020DH\u0014¢\u0006\u0002\u0010QJ\u001d\u0010k\u001a\u00020$2\u0006\u0010E\u001a\u00028\u00042\u0006\u0010J\u001a\u00020DH\u0014¢\u0006\u0002\u0010SJ\u001d\u0010l\u001a\u00020.2\u0006\u0010E\u001a\u00028\u00062\u0006\u0010J\u001a\u00020DH\u0014¢\u0006\u0002\u0010fJ\u001d\u0010m\u001a\u00020B2\u0006\u0010E\u001a\u00028\u00022\u0006\u0010J\u001a\u00020DH\u0014¢\u0006\u0002\u0010nJ\u001d\u0010o\u001a\u00020'2\u0006\u0010E\u001a\u00028\b2\u0006\u0010J\u001a\u00020DH\u0014¢\u0006\u0002\u0010pJ\u001f\u0010q\u001a\u00020=2\u0006\u0010E\u001a\u00028\u00032\b\u0010J\u001a\u0004\u0018\u00010DH\u0014¢\u0006\u0002\u0010rJ\u001f\u0010s\u001a\u00020B2\u0006\u0010E\u001a\u00028\u00022\b\u0010J\u001a\u0004\u0018\u00010DH\u0004¢\u0006\u0002\u0010nJ\u001f\u0010t\u001a\u00020'2\u0006\u0010E\u001a\u00028\b2\b\u0010J\u001a\u0004\u0018\u00010DH\u0004¢\u0006\u0002\u0010pJ\u0086\u0002\u0010u\u001a\u0002Hv\"\b\b\t\u0010w*\u00028\u0000\"\u0012\b\n\u0010x*\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002Hv0y\"\b\b\u000b\u0010v*\u00020z2\u0006\u0010E\u001a\u0002Hw2\u0018\u0010{\u001a\u0014\u0012\u0004\u0012\u0002Hw\u0012\u0004\u0012\u0002Hv\u0012\u0004\u0012\u0002Hx0|2N\u0010}\u001aJ\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020D\u0012\n\u0012\b\u0012\u0004\u0012\u0002Hx0\u007f\u0012\"\u0012 \u0012\u0004\u0012\u0002Hx\u0012\u0004\u0012\u0002Hv0\u0080\u0001j\u000f\u0012\u0004\u0012\u0002Hx\u0012\u0004\u0012\u0002Hv`\u0081\u0001\u0012\u0004\u0012\u0002Hv0~¢\u0006\u0003\b\u0082\u00012\u0018\b\u0004\u0010\u0083\u0001\u001a\u0011\u0012\u0006\u0012\u0004\u0018\u00010D\u0012\u0004\u0012\u0002Hx0\u0080\u00012'\b\b\u0010\u0084\u0001\u001a \u0012\u0004\u0012\u0002Hx\u0012\u0004\u0012\u0002Hv0\u0080\u0001j\u000f\u0012\u0004\u0012\u0002Hx\u0012\u0004\u0012\u0002Hv`\u0081\u00012\u0018\b\u0002\u0010\u0085\u0001\u001a\u0011\u0012\u0004\u0012\u0002Hw\u0012\u0006\u0012\u0004\u0018\u00010D0\u0080\u0001H\u0082\b¢\u0006\u0003\u0010\u0086\u0001Jç\u0002\u0010u\u001a\u0002Hv\"\b\b\t\u0010w*\u00028\u0000\"\u0012\b\n\u0010x*\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002Hv0y\"\b\b\u000b\u0010v*\u00020z2\u0006\u0010E\u001a\u0002Hw2\u0018\u0010{\u001a\u0014\u0012\u0004\u0012\u0002Hw\u0012\u0004\u0012\u0002Hv\u0012\u0004\u0012\u0002Hx0|2N\u0010}\u001aJ\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020D\u0012\n\u0012\b\u0012\u0004\u0012\u0002Hx0\u007f\u0012\"\u0012 \u0012\u0004\u0012\u0002Hx\u0012\u0004\u0012\u0002Hv0\u0080\u0001j\u000f\u0012\u0004\u0012\u0002Hx\u0012\u0004\u0012\u0002Hv`\u0081\u0001\u0012\u0004\u0012\u0002Hv0~¢\u0006\u0003\b\u0082\u00012a\u0010\u0087\u0001\u001a\\\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002Hw\u0012\u0004\u0012\u0002Hv\u0012\u0004\u0012\u0002Hx0|\u0012\u0004\u0012\u0002Hw\u0012\n\u0012\b\u0012\u0004\u0012\u0002Hx0\u007f\u0012\"\u0012 \u0012\u0004\u0012\u0002Hx\u0012\u0004\u0012\u0002Hv0\u0080\u0001j\u000f\u0012\u0004\u0012\u0002Hx\u0012\u0004\u0012\u0002Hv`\u0081\u0001\u0012\u0004\u0012\u0002Hv0~¢\u0006\u0003\b\u0082\u00012\u0018\b\u0004\u0010\u0083\u0001\u001a\u0011\u0012\u0006\u0012\u0004\u0018\u00010D\u0012\u0004\u0012\u0002Hx0\u0080\u00012'\b\b\u0010\u0084\u0001\u001a \u0012\u0004\u0012\u0002Hx\u0012\u0004\u0012\u0002Hv0\u0080\u0001j\u000f\u0012\u0004\u0012\u0002Hx\u0012\u0004\u0012\u0002Hv`\u0081\u00012\u0016\u0010\u0085\u0001\u001a\u0011\u0012\u0004\u0012\u0002Hw\u0012\u0006\u0012\u0004\u0018\u00010D0\u0080\u0001H\u0082\b¢\u0006\u0003\u0010\u0088\u0001J+\u0010\u0089\u0001\u001a\u00020\u00172\u0006\u0010E\u001a\u00028\u00012\u0014\u0010\u008a\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00170\u0080\u0001¢\u0006\u0003\u0010\u008b\u0001J+\u0010\u008c\u0001\u001a\u00020\u00172\u0006\u0010E\u001a\u00028\u00012\u0014\u0010\u008a\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00170\u0080\u0001¢\u0006\u0003\u0010\u008b\u0001J+\u0010\u008d\u0001\u001a\u00020\u001a2\u0006\u0010E\u001a\u00028\u00052\u0014\u0010\u008e\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001a0\u0080\u0001¢\u0006\u0003\u0010\u008f\u0001J+\u0010\u0090\u0001\u001a\u00020\u001a2\u0006\u0010E\u001a\u00028\u00052\u0014\u0010\u008e\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001a0\u0080\u0001¢\u0006\u0003\u0010\u008f\u0001J+\u0010\u0091\u0001\u001a\u00020\u001d2\u0006\u0010E\u001a\u00028\u00012\u0014\u0010\u0092\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001d0\u0080\u0001¢\u0006\u0003\u0010\u0093\u0001J3\u0010\u0091\u0001\u001a\u00020\u001d2\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\b\u0010\u0096\u0001\u001a\u00030\u0095\u00012\b\u0010\u0097\u0001\u001a\u00030\u0098\u00012\u0006\u0010E\u001a\u00028\u0001¢\u0006\u0003\u0010\u0099\u0001J+\u0010\u009a\u0001\u001a\u00020\u001d2\u0006\u0010E\u001a\u00028\u00012\u0014\u0010\u0092\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001d0\u0080\u0001¢\u0006\u0003\u0010\u0093\u0001Jc\u0010\u009b\u0001\u001a\u00020 2\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\b\u0010\u0096\u0001\u001a\u00030\u0095\u00012\b\u0010\u0097\u0001\u001a\u00030\u0098\u00012\u0006\u0010E\u001a\u00028\u00062\b\u0010\u009c\u0001\u001a\u00030\u009d\u00012\f\b\u0002\u0010\u009e\u0001\u001a\u0005\u0018\u00010\u009f\u00012\u0016\b\u0002\u0010 \u0001\u001a\u000f\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020 0\u0080\u0001¢\u0006\u0003\u0010¡\u0001JI\u0010\u009b\u0001\u001a\u00020 2\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\b\u0010\u0096\u0001\u001a\u00030\u0095\u00012\b\u0010\u0097\u0001\u001a\u00030\u0098\u00012\u0006\u0010E\u001a\u00028\u00062\b\u0010\u009c\u0001\u001a\u00030\u009d\u00012\n\u0010¢\u0001\u001a\u0005\u0018\u00010£\u0001¢\u0006\u0003\u0010¤\u0001J+\u0010¥\u0001\u001a\u00020&2\u0006\u0010E\u001a\u00028\b2\u0014\u0010¦\u0001\u001a\u000f\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020&0\u0080\u0001¢\u0006\u0003\u0010§\u0001J3\u0010¥\u0001\u001a\u00020&2\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\b\u0010\u0096\u0001\u001a\u00030\u0095\u00012\b\u0010\u0097\u0001\u001a\u00030\u0098\u00012\u0006\u0010E\u001a\u00028\b¢\u0006\u0003\u0010¨\u0001J\u0087\u0002\u0010©\u0001\u001a\u0002Hv\"\b\b\t\u0010w*\u00028\u0000\"\u0012\b\n\u0010x*\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002Hv0y\"\b\b\u000b\u0010v*\u00020z2\u0006\u0010E\u001a\u0002Hw2\u0018\u0010{\u001a\u0014\u0012\u0004\u0012\u0002Hw\u0012\u0004\u0012\u0002Hv\u0012\u0004\u0012\u0002Hx0|2N\u0010}\u001aJ\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020D\u0012\n\u0012\b\u0012\u0004\u0012\u0002Hx0\u007f\u0012\"\u0012 \u0012\u0004\u0012\u0002Hx\u0012\u0004\u0012\u0002Hv0\u0080\u0001j\u000f\u0012\u0004\u0012\u0002Hx\u0012\u0004\u0012\u0002Hv`\u0081\u0001\u0012\u0004\u0012\u0002Hv0~¢\u0006\u0003\b\u0082\u00012\u0018\b\u0004\u0010\u0083\u0001\u001a\u0011\u0012\u0006\u0012\u0004\u0018\u00010D\u0012\u0004\u0012\u0002Hx0\u0080\u00012'\b\b\u0010\u0084\u0001\u001a \u0012\u0004\u0012\u0002Hx\u0012\u0004\u0012\u0002Hv0\u0080\u0001j\u000f\u0012\u0004\u0012\u0002Hx\u0012\u0004\u0012\u0002Hv`\u0081\u00012\u0018\b\u0002\u0010\u0085\u0001\u001a\u0011\u0012\u0004\u0012\u0002Hw\u0012\u0006\u0012\u0004\u0018\u00010D0\u0080\u0001H\u0082\b¢\u0006\u0003\u0010\u0086\u0001J+\u0010ª\u0001\u001a\u00020-2\u0006\u0010E\u001a\u00028\u00062\u0014\u0010«\u0001\u001a\u000f\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020-0\u0080\u0001¢\u0006\u0003\u0010¬\u0001J=\u0010ª\u0001\u001a\u00020-2\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\b\u0010\u0096\u0001\u001a\u00030\u0095\u00012\b\u0010\u0097\u0001\u001a\u00030\u0098\u00012\u0006\u0010E\u001a\u00028\u00062\b\u0010\u00ad\u0001\u001a\u00030®\u0001¢\u0006\u0003\u0010¯\u0001J+\u0010°\u0001\u001a\u00020-2\u0006\u0010E\u001a\u00028\u00062\u0014\u0010«\u0001\u001a\u000f\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020-0\u0080\u0001¢\u0006\u0003\u0010¬\u0001JK\u0010±\u0001\u001a\u00020&2\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\b\u0010\u0096\u0001\u001a\u00030\u0095\u00012\b\u0010\u0097\u0001\u001a\u00030\u0098\u00012\u0006\u0010E\u001a\u00028\b2\u0016\b\u0002\u0010¦\u0001\u001a\u000f\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020&0\u0080\u0001¢\u0006\u0003\u0010²\u0001J)\u0010³\u0001\u001a\u00020<2\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\b\u0010\u0096\u0001\u001a\u00030\u0095\u00012\u0006\u0010E\u001a\u00028\u0003¢\u0006\u0003\u0010´\u0001J+\u0010µ\u0001\u001a\u00020#2\u0006\u0010E\u001a\u00028\u00042\u0014\u0010¶\u0001\u001a\u000f\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020#0\u0080\u0001¢\u0006\u0003\u0010·\u0001J+\u0010¸\u0001\u001a\u00020#2\u0006\u0010E\u001a\u00028\u00042\u0014\u0010¶\u0001\u001a\u000f\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020#0\u0080\u0001¢\u0006\u0003\u0010·\u0001J+\u0010¹\u0001\u001a\u00020A2\u0006\u0010E\u001a\u00028\u00022\u0014\u0010º\u0001\u001a\u000f\u0012\u0004\u0012\u00020B\u0012\u0004\u0012\u00020A0\u0080\u0001¢\u0006\u0003\u0010»\u0001J+\u0010¼\u0001\u001a\u00020A2\u0006\u0010E\u001a\u00028\u00022\u0014\u0010º\u0001\u001a\u000f\u0012\u0004\u0012\u00020B\u0012\u0004\u0012\u00020A0\u0080\u0001¢\u0006\u0003\u0010»\u0001J?\u0010½\u0001\u001a\u00020\u001d2\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\b\u0010\u0096\u0001\u001a\u00030\u0095\u00012\b\u0010\u0097\u0001\u001a\u00030\u0098\u00012\u0007\u0010¾\u0001\u001a\u00028\u00012\u0007\u0010¿\u0001\u001a\u00020\u001eH$¢\u0006\u0003\u0010À\u0001JT\u0010Á\u0001\u001a\u00020 2\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\b\u0010\u0096\u0001\u001a\u00030\u0095\u00012\b\u0010\u0097\u0001\u001a\u00030\u0098\u00012\u0006\u0010E\u001a\u00028\u00062\b\u0010\u009c\u0001\u001a\u00030\u009d\u00012\n\u0010\u009e\u0001\u001a\u0005\u0018\u00010\u009f\u00012\u0007\u0010¿\u0001\u001a\u00020!H$¢\u0006\u0003\u0010Â\u0001JH\u0010Ã\u0001\u001a\u00020-2\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\b\u0010\u0096\u0001\u001a\u00030\u0095\u00012\b\u0010\u0097\u0001\u001a\u00030\u0098\u00012\u0006\u0010E\u001a\u00028\u00062\b\u0010\u00ad\u0001\u001a\u00030®\u00012\u0007\u0010¿\u0001\u001a\u00020.H$¢\u0006\u0003\u0010Ä\u0001J5\u0010Å\u0001\u001a\u00020<2\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\b\u0010\u0096\u0001\u001a\u00030\u0095\u00012\u0007\u0010Æ\u0001\u001a\u00028\u00032\u0007\u0010¿\u0001\u001a\u00020=H$¢\u0006\u0003\u0010Ç\u0001J>\u0010È\u0001\u001a\u00020&2\b\u0010\u0094\u0001\u001a\u00030\u0095\u00012\b\u0010\u0096\u0001\u001a\u00030\u0095\u00012\b\u0010\u0097\u0001\u001a\u00030\u0098\u00012\u0006\u0010E\u001a\u00028\b2\u0007\u0010¿\u0001\u001a\u00020'H$¢\u0006\u0003\u0010É\u0001J\u0012\u0010Ê\u0001\u001a\u00030Ë\u00012\b\u0010Ì\u0001\u001a\u00030Í\u0001J\u0011\u0010Ê\u0001\u001a\u00030Ë\u00012\u0007\u0010¿\u0001\u001a\u00020\u0010J!\u0010Î\u0001\u001a\u00030Ë\u00012\u0015\u0010Ï\u0001\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0005\u0012\u00030Ë\u00010\u0080\u0001H\u0007J\u0012\u0010Ð\u0001\u001a\u00030Ë\u00012\b\u0010Ì\u0001\u001a\u00030Í\u0001J\u0011\u0010Ð\u0001\u001a\u00030Ë\u00012\u0007\u0010¿\u0001\u001a\u00020\u0010J\u0083\u0002\u0010Ñ\u0001\u001a\u0002Hx\"\b\b\t\u0010w*\u00028\u0000\"\u0012\b\n\u0010x*\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002Hv0y\"\b\b\u000b\u0010v*\u00020z2\u0006\u0010E\u001a\u0002Hw2\u0018\u0010{\u001a\u0014\u0012\u0004\u0012\u0002Hw\u0012\u0004\u0012\u0002Hv\u0012\u0004\u0012\u0002Hx0|27\u0010Ò\u0001\u001a2\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020D\u0012\n\u0012\b\u0012\u0004\u0012\u0002Hx0\u007f\u0012\n\u0012\b\u0012\u0004\u0012\u0002Hx0\u007f\u0012\u0004\u0012\u0002Hx0~¢\u0006\u0003\b\u0082\u00012\u001e\b\u0004\u0010\u0083\u0001\u001a\u0017\u0012\u0004\u0012\u0002Hw\u0012\u0006\u0012\u0004\u0018\u00010D\u0012\u0004\u0012\u0002Hx0Ó\u00012\u001c\b\u0004\u0010Ô\u0001\u001a\u0015\u0012\u0004\u0012\u0002Hw\u0012\u0004\u0012\u00020D\u0012\u0004\u0012\u0002Hx0Ó\u00012\u0016\b\u0004\u0010Õ\u0001\u001a\u000f\u0012\u0004\u0012\u0002Hw\u0012\u0004\u0012\u0002Hx0\u0080\u00012\u0018\b\u0002\u0010\u0085\u0001\u001a\u0011\u0012\u0004\u0012\u0002Hw\u0012\u0006\u0012\u0004\u0018\u00010D0\u0080\u0001H\u0082\b¢\u0006\u0003\u0010Ö\u0001J\u0016\u0010×\u0001\u001a\u00020\u00182\u0006\u0010E\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010VJ\u0016\u0010Ø\u0001\u001a\u00020\u001b2\u0006\u0010E\u001a\u00028\u0005H\u0016¢\u0006\u0002\u0010XJ\u0016\u0010Ù\u0001\u001a\u00020$2\u0006\u0010E\u001a\u00028\u0004H\u0016¢\u0006\u0002\u0010^J\u0016\u0010Ú\u0001\u001a\u00020\u001e2\u0006\u0010E\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010ZJ\u0016\u0010Û\u0001\u001a\u00020!2\u0006\u0010E\u001a\u00028\u0006H\u0016¢\u0006\u0002\u0010\\J\u0016\u0010Ü\u0001\u001a\u00020.2\u0006\u0010E\u001a\u00028\u0006H\u0016¢\u0006\u0002\u0010`J\u0016\u0010Ý\u0001\u001a\u00020'2\u0006\u0010E\u001a\u00028\bH\u0016¢\u0006\u0002\u0010dJ\u0017\u0010Þ\u0001\u001a\u00020=2\u0006\u0010E\u001a\u00028\u0003H\u0016¢\u0006\u0003\u0010ß\u0001J\u0016\u0010à\u0001\u001a\u00020$2\u0006\u0010E\u001a\u00028\u0004H\u0016¢\u0006\u0002\u0010^J\u0016\u0010á\u0001\u001a\u00020B2\u0006\u0010E\u001a\u00028\u0002H\u0016¢\u0006\u0002\u0010bJ\u0016\u0010â\u0001\u001a\u00020'2\u0006\u0010E\u001a\u00028\bH\u0016¢\u0006\u0002\u0010dJ\u0018\u0010ã\u0001\u001a\u00030ä\u00012\u0006\u0010E\u001a\u00028\u0007H\u0016¢\u0006\u0003\u0010å\u0001J!\u0010æ\u0001\u001a\u00030Ë\u0001*\u0015\u0012\u0010\u0012\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u0003010ç\u0001H$R\u001d\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8F¢\u0006\f\u0012\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R \u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00180\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00028\u0005\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001b0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u001c\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u001f\u001a\u0014\u0012\u0004\u0012\u00028\u0006\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020!0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\"\u001a\u0014\u0012\u0004\u0012\u00028\u0004\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010%\u001a\u0014\u0012\u0004\u0012\u00028\b\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020'0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010(\u001a\u00020)8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+R \u0010,\u001a\u0014\u0012\u0004\u0012\u00028\u0006\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020.0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R-\u0010/\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u000301008BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b4\u00105\u001a\u0004\b2\u00103R-\u00106\u001a\u0014\u0012\u0004\u0012\u00028\b\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020'018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b9\u0010:\u001a\u0004\b7\u00108R \u0010;\u001a\u0014\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020=0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b>\u0010?R \u0010@\u001a\u0014\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00020A\u0012\u0004\u0012\u00020B0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006è\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/SymbolTableExtension;", "Declaration", "Class", "TypeAlias", "Script", "Function", "Constructor", "Property", "ValueParameter", "TypeParameter", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTableExtension;", "table", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "(Lorg/jetbrains/kotlin/ir/util/SymbolTable;)V", "allUnboundSymbols", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getAllUnboundSymbols$annotations", "()V", "getAllUnboundSymbols", "()Ljava/util/Set;", "classSlice", "Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice$Flat;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "constructorSlice", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "enumEntrySlice", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "fieldSlice", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "functionSlice", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "globalTypeParameterSlice", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "propertySlice", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "scopedSlices", "", "Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice$Scoped;", "getScopedSlices", "()Ljava/util/List;", "scopedSlices$delegate", "Lkotlin/Lazy;", "scopedTypeParameterSlice", "getScopedTypeParameterSlice", "()Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice$Scoped;", "scopedTypeParameterSlice$delegate", "Lkotlin/properties/ReadWriteProperty;", "scriptSlice", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "getTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeAliasSlice", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "calculateEnumEntrySignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "declaration", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/util/IdSignature;", "calculateFieldSignature", "calculateSignature", "createClassSymbol", "signature", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/util/IdSignature;)Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "createConstructorSymbol", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/util/IdSignature;)Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "createEnumEntrySymbol", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/util/IdSignature;)Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "createFieldSymbol", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/util/IdSignature;)Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "createFunctionSymbol", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/util/IdSignature;)Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "createPrivateClassSymbol", "descriptor", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "createPrivateConstructorSymbol", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "createPrivateEnumEntrySymbol", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "createPrivateFieldSymbol", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "createPrivateFunctionSymbol", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "createPrivatePropertySymbol", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "createPrivateTypeAliasSymbol", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "createPrivateTypeParameterSymbol", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "createPropertySymbol", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/util/IdSignature;)Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "createPublicClassSymbol", "createPublicConstructorSymbol", "createPublicEnumEntrySymbol", "createPublicFieldSymbol", "createPublicFunctionSymbol", "createPublicPropertySymbol", "createPublicTypeAliasSymbol", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/util/IdSignature;)Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "createPublicTypeParameterSymbol", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/util/IdSignature;)Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "createScriptSymbol", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/util/IdSignature;)Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "createTypeAliasSymbol", "createTypeParameterSymbol", "declare", "SymbolOwner", "D", NativeSymbol.CLASS_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "slice", "Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice;", "declareBySignature", "Lkotlin/Function4;", "Lkotlin/Function0;", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/util/OwnerFactory;", "Lkotlin/ExtensionFunctionType;", "symbolFactory", "ownerFactory", "specificCalculateSignature", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice;Lkotlin/jvm/functions/Function4;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "declareByDeclaration", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice;Lkotlin/jvm/functions/Function4;Lkotlin/jvm/functions/Function4;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "declareClass", "classFactory", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "declareClassIfNotExists", "declareConstructor", "constructorFactory", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "declareConstructorIfNotExists", "declareEnumEntry", "enumEntryFactory", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "declareEnumEntryIfNotExists", "declareField", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "fieldFactory", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrField;", "irInitializer", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;)Lorg/jetbrains/kotlin/ir/declarations/IrField;", "declareGlobalTypeParameter", "typeParameterFactory", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "declareIfNotExist", "declareProperty", "propertyFactory", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "isDelegated", "", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Ljava/lang/Object;Z)Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "declarePropertyIfNotExists", "declareScopedTypeParameter", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "declareScript", "(IILjava/lang/Object;)Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "declareSimpleFunction", "functionFactory", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "declareSimpleFunctionIfNotExists", "declareTypeAlias", "typeAliasFactory", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "declareTypeAliasIfNotExists", "defaultEnumEntryFactory", "enumEntry", NativeSymbol.TYPE_NAME, "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;)Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "defaultFieldFactory", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;)Lorg/jetbrains/kotlin/ir/declarations/IrField;", "defaultPropertyFactory", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Ljava/lang/Object;ZLorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;)Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "defaultScriptFactory", "script", "(IILjava/lang/Object;Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;)Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "defaultTypeParameterFactory", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;)Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "enterScope", "", "owner", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "forEachDeclarationSymbol", "block", "leaveScope", "reference", "referenceBySignature", "Lkotlin/Function2;", "publicSymbolFactory", "privateSymbolFactory", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice;Lkotlin/jvm/functions/Function4;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "referenceClass", "referenceConstructor", "referenceDeclaredFunction", "referenceEnumEntry", "referenceField", "referenceProperty", "referenceScopedTypeParameter", "referenceScript", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "referenceSimpleFunction", "referenceTypeAlias", "referenceTypeParameter", "referenceValueParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "initializeScopedSlices", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SymbolTableExtension<Declaration, Class extends Declaration, TypeAlias extends Declaration, Script extends Declaration, Function extends Declaration, Constructor extends Declaration, Property extends Declaration, ValueParameter extends Declaration, TypeParameter extends Declaration> extends ReferenceSymbolTableExtension<Class, TypeAlias, Script, Function, Constructor, Property, ValueParameter, TypeParameter> {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(SymbolTableExtension.class), "scopedTypeParameterSlice", "getScopedTypeParameterSlice()Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice$Scoped;"))};
    private final SymbolTableSlice.Flat<Class, IrClass, IrClassSymbol> classSlice;
    private final SymbolTableSlice.Flat<Constructor, IrConstructor, IrConstructorSymbol> constructorSlice;
    private final SymbolTableSlice.Flat<Class, IrEnumEntry, IrEnumEntrySymbol> enumEntrySlice;
    private final SymbolTableSlice.Flat<Property, IrField, IrFieldSymbol> fieldSlice;
    private final SymbolTableSlice.Flat<Function, IrSimpleFunction, IrSimpleFunctionSymbol> functionSlice;
    private final SymbolTableSlice.Flat<TypeParameter, IrTypeParameter, IrTypeParameterSymbol> globalTypeParameterSlice;
    private final SymbolTableSlice.Flat<Property, IrProperty, IrPropertySymbol> propertySlice;

    /* JADX INFO: renamed from: scopedSlices$delegate, reason: from kotlin metadata */
    private final Lazy scopedSlices;

    /* JADX INFO: renamed from: scopedTypeParameterSlice$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty scopedTypeParameterSlice;
    private final SymbolTableSlice.Flat<Script, IrScript, IrScriptSymbol> scriptSlice;
    private final SymbolTable table;
    private final SymbolTableSlice.Flat<TypeAlias, IrTypeAlias, IrTypeAliasSymbol> typeAliasSlice;

    public static /* synthetic */ void getAllUnboundSymbols$annotations() {
    }

    protected abstract IdSignature calculateEnumEntrySignature(Class declaration);

    protected abstract IdSignature calculateFieldSignature(Property declaration);

    protected abstract IdSignature calculateSignature(Declaration declaration);

    protected abstract IrEnumEntry defaultEnumEntryFactory(int startOffset, int endOffset, IrDeclarationOrigin origin, Class enumEntry, IrEnumEntrySymbol symbol);

    protected abstract IrField defaultFieldFactory(int startOffset, int endOffset, IrDeclarationOrigin origin, Property declaration, IrType type, DescriptorVisibility visibility, IrFieldSymbol symbol);

    protected abstract IrProperty defaultPropertyFactory(int startOffset, int endOffset, IrDeclarationOrigin origin, Property declaration, boolean isDelegated, IrPropertySymbol symbol);

    protected abstract IrScript defaultScriptFactory(int startOffset, int endOffset, Script script, IrScriptSymbol symbol);

    protected abstract IrTypeParameter defaultTypeParameterFactory(int startOffset, int endOffset, IrDeclarationOrigin origin, TypeParameter declaration, IrTypeParameterSymbol symbol);

    protected abstract void initializeScopedSlices(List<SymbolTableSlice.Scoped<?, ?, ?>> list);

    public final SymbolTable getTable() {
        return this.table;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SymbolTableExtension(SymbolTable table) {
        Intrinsics.checkNotNullParameter(table, "table");
        this.table = table;
        int i = 2;
        this.scriptSlice = new SymbolTableSlice.Flat<>(getLock(), null, i, 0 == true ? 1 : 0);
        this.classSlice = new SymbolTableSlice.Flat<>(getLock(), 0 == true ? 1 : 0, i, 0 == true ? 1 : 0);
        this.constructorSlice = new SymbolTableSlice.Flat<>(getLock(), 0 == true ? 1 : 0, i, 0 == true ? 1 : 0);
        this.enumEntrySlice = new SymbolTableSlice.Flat<>(getLock(), 0 == true ? 1 : 0, i, 0 == true ? 1 : 0);
        this.fieldSlice = new SymbolTableSlice.Flat<>(getLock(), 0 == true ? 1 : 0, i, 0 == true ? 1 : 0);
        this.functionSlice = new SymbolTableSlice.Flat<>(getLock(), 0 == true ? 1 : 0, i, 0 == true ? 1 : 0);
        this.propertySlice = new SymbolTableSlice.Flat<>(getLock(), 0 == true ? 1 : 0, i, 0 == true ? 1 : 0);
        this.typeAliasSlice = new SymbolTableSlice.Flat<>(getLock(), 0 == true ? 1 : 0, i, 0 == true ? 1 : 0);
        this.globalTypeParameterSlice = new SymbolTableSlice.Flat<>(getLock(), 0 == true ? 1 : 0, i, 0 == true ? 1 : 0);
        this.scopedTypeParameterSlice = ThreadLocalKt.threadLocal(new Function0<SymbolTableSlice.Scoped<TypeParameter, IrTypeParameter, IrTypeParameterSymbol>>(this) { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$scopedTypeParameterSlice$2
            final /* synthetic */ SymbolTableExtension<Declaration, Class, TypeAlias, Script, Function, Constructor, Property, ValueParameter, TypeParameter> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final SymbolTableSlice.Scoped<TypeParameter, IrTypeParameter, IrTypeParameterSymbol> invoke() {
                return new SymbolTableSlice.Scoped<>(this.this$0.getLock());
            }
        });
        this.scopedSlices = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<List<? extends SymbolTableSlice.Scoped<?, ?, ?>>>(this) { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$scopedSlices$2
            final /* synthetic */ SymbolTableExtension<Declaration, Class, TypeAlias, Script, Function, Constructor, Property, ValueParameter, TypeParameter> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            /* JADX WARN: Type inference incomplete: some casts might be missing */
            @Override // kotlin.jvm.functions.Function0
            public final List<? extends SymbolTableSlice.Scoped<?, ?, ?>> invoke() {
                SymbolTableExtension<Declaration, Class, TypeAlias, Script, Function, Constructor, Property, ValueParameter, TypeParameter> symbolTableExtension = this.this$0;
                List<SymbolTableSlice.Scoped<?, ?, ?>> listCreateListBuilder = CollectionsKt.createListBuilder();
                listCreateListBuilder.add(symbolTableExtension.getScopedTypeParameterSlice());
                symbolTableExtension.initializeScopedSlices(listCreateListBuilder);
                return CollectionsKt.build(listCreateListBuilder);
            }
        });
    }

    protected final IrLock getLock() {
        return this.table.getLock();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SymbolTableSlice.Scoped<TypeParameter, IrTypeParameter, IrTypeParameterSymbol> getScopedTypeParameterSlice() {
        return (SymbolTableSlice.Scoped) this.scopedTypeParameterSlice.getValue(this, $$delegatedProperties[0]);
    }

    private final List<SymbolTableSlice.Scoped<?, ?, ?>> getScopedSlices() {
        return (List) this.scopedSlices.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrScript declareScript(final int startOffset, final int endOffset, final Script declaration) {
        IrScript irScriptDeclareScript;
        SymbolTableSlice.Flat<Script, IrScript, IrScriptSymbol> flat = this.scriptSlice;
        Function1<? super IrScriptSymbol, ? extends IrScript> function1 = new Function1<IrScriptSymbol, IrScript>(this) { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension.declareScript.3
            final /* synthetic */ SymbolTableExtension<Declaration, Class, TypeAlias, Script, Function, Constructor, Property, ValueParameter, TypeParameter> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrScript invoke(IrScriptSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.defaultScriptFactory(startOffset, endOffset, declaration, it);
            }
        };
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature != null) {
            irScriptDeclareScript = getTable().declareScript(idSignatureCalculateSignature, new Function0<IrScriptSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareScript$$inlined$declare$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrScriptSymbol invoke() {
                    return this.createScriptSymbol(declaration, idSignatureCalculateSignature);
                }
            }, function1);
        } else {
            Function0<IrScriptSymbol> function0 = new Function0<IrScriptSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareScript$$inlined$declare$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrScriptSymbol invoke() {
                    return this.createScriptSymbol(declaration, idSignatureCalculateSignature);
                }
            };
            synchronized (flat.getLock()) {
                IrScriptSymbol irScriptSymbol = flat.get(declaration);
                if (irScriptSymbol == null) {
                    IrScriptSymbol irScriptSymbolInvoke = function0.invoke();
                    flat.set(declaration, irScriptSymbolInvoke);
                    irScriptSymbol = irScriptSymbolInvoke;
                } else {
                    flat.getUnboundSymbols().remove(irScriptSymbol);
                }
                irScriptDeclareScript = function1.invoke(irScriptSymbol);
                if (!irScriptSymbol.isBound()) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (irScriptSymbol.getOwner() != irScriptDeclareScript) {
                    throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irScriptSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irScriptDeclareScript, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
            }
        }
        return irScriptDeclareScript;
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
    public IrScriptSymbol referenceScript(Script declaration) {
        IrScriptSymbol irScriptSymbolCreateScriptSymbol;
        SymbolTableSlice.Flat<Script, IrScript, IrScriptSymbol> flat = this.scriptSlice;
        synchronized (flat.getLock()) {
            irScriptSymbolCreateScriptSymbol = flat.get(declaration);
            if (irScriptSymbolCreateScriptSymbol == null) {
                irScriptSymbolCreateScriptSymbol = createScriptSymbol(declaration, null);
                flat.getUnboundSymbols().add(irScriptSymbolCreateScriptSymbol);
                flat.set(declaration, irScriptSymbolCreateScriptSymbol);
            }
        }
        return irScriptSymbolCreateScriptSymbol;
    }

    protected IrScriptSymbol createScriptSymbol(Script declaration, IdSignature signature) {
        return new IrScriptSymbolImpl(null, 1, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrClass declareClass(final Class declaration, Function1<? super IrClassSymbol, ? extends IrClass> classFactory) {
        IrClass irClassDeclareClass;
        Intrinsics.checkNotNullParameter(classFactory, "classFactory");
        SymbolTableSlice.Flat<Class, IrClass, IrClassSymbol> flat = this.classSlice;
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature != null) {
            irClassDeclareClass = getTable().declareClass(idSignatureCalculateSignature, new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareClass$$inlined$declare$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrClassSymbol invoke() {
                    return this.createClassSymbol(declaration, idSignatureCalculateSignature);
                }
            }, classFactory);
        } else {
            Function0<IrClassSymbol> function0 = new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareClass$$inlined$declare$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrClassSymbol invoke() {
                    return this.createClassSymbol(declaration, idSignatureCalculateSignature);
                }
            };
            synchronized (flat.getLock()) {
                IrClassSymbol irClassSymbol = flat.get(declaration);
                if (irClassSymbol == null) {
                    IrClassSymbol irClassSymbolInvoke = function0.invoke();
                    flat.set(declaration, irClassSymbolInvoke);
                    irClassSymbol = irClassSymbolInvoke;
                } else {
                    flat.getUnboundSymbols().remove(irClassSymbol);
                }
                irClassDeclareClass = classFactory.invoke(irClassSymbol);
                if (!irClassSymbol.isBound()) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (irClassSymbol.getOwner() != irClassDeclareClass) {
                    throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irClassSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irClassDeclareClass, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
            }
        }
        return irClassDeclareClass;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrClass declareClassIfNotExists(final Class declaration, Function1<? super IrClassSymbol, ? extends IrClass> classFactory) {
        IrClass irClassDeclareClassIfNotExists;
        Intrinsics.checkNotNullParameter(classFactory, "classFactory");
        SymbolTableSlice.Flat<Class, IrClass, IrClassSymbol> flat = this.classSlice;
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature != null) {
            irClassDeclareClassIfNotExists = getTable().declareClassIfNotExists(idSignatureCalculateSignature, new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareClassIfNotExists$$inlined$declareIfNotExist$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrClassSymbol invoke() {
                    return this.createClassSymbol(declaration, idSignatureCalculateSignature);
                }
            }, classFactory);
        } else {
            Function0<IrClassSymbol> function0 = new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareClassIfNotExists$$inlined$declareIfNotExist$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrClassSymbol invoke() {
                    return this.createClassSymbol(declaration, idSignatureCalculateSignature);
                }
            };
            synchronized (flat.getLock()) {
                IrClassSymbol irClassSymbolInvoke = flat.get(declaration);
                if (irClassSymbolInvoke == null) {
                    irClassSymbolInvoke = function0.invoke();
                    flat.set(declaration, irClassSymbolInvoke);
                } else if (irClassSymbolInvoke.isBound()) {
                    flat.getUnboundSymbols().remove(irClassSymbolInvoke);
                    irClassDeclareClassIfNotExists = irClassSymbolInvoke.getOwner();
                }
                irClassDeclareClassIfNotExists = classFactory.invoke(irClassSymbolInvoke);
                if (!irClassSymbolInvoke.isBound()) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (irClassSymbolInvoke.getOwner() != irClassDeclareClassIfNotExists) {
                    throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irClassSymbolInvoke.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irClassDeclareClassIfNotExists, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
            }
        }
        return irClassDeclareClassIfNotExists;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
    public IrClassSymbol referenceClass(final Class declaration) {
        IrClassSymbol irClassSymbolInvoke;
        IrClassSymbol irClassSymbolCreateClassSymbol;
        Object obj;
        SymbolTableSlice.Flat<Class, IrClass, IrClassSymbol> flat = this.classSlice;
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature == null) {
            synchronized (flat.getLock()) {
                irClassSymbolCreateClassSymbol = flat.get(declaration);
                if (irClassSymbolCreateClassSymbol == null) {
                    irClassSymbolCreateClassSymbol = createClassSymbol(declaration, idSignatureCalculateSignature);
                    flat.getUnboundSymbols().add(irClassSymbolCreateClassSymbol);
                    flat.set(declaration, irClassSymbolCreateClassSymbol);
                }
            }
        } else {
            SymbolTable table = getTable();
            Function0<IrClassSymbol> function0 = new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$referenceClass$$inlined$reference$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrClassSymbol invoke() {
                    return this.createPublicClassSymbol(declaration, idSignatureCalculateSignature);
                }
            };
            Function0<IrClassSymbol> function1 = new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$referenceClass$$inlined$reference$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrClassSymbol invoke() {
                    return this.createPrivateClassSymbol(declaration);
                }
            };
            Function0<IrClassSymbol> function2 = function0;
            if (idSignatureCalculateSignature.isPubliclyVisible()) {
                SymbolTableSlice.Flat flat2 = table.classSlice;
                synchronized (flat2.getLock()) {
                    obj = flat2.get(idSignatureCalculateSignature);
                    if (obj == null) {
                        obj = (IrBindableSymbol) ((IrClassSymbol) function2.invoke());
                        flat2.getUnboundSymbols().add(obj);
                        flat2.set(idSignatureCalculateSignature, obj);
                    }
                }
                irClassSymbolInvoke = (IrClassSymbol) obj;
            } else {
                irClassSymbolInvoke = function1.invoke();
            }
            irClassSymbolCreateClassSymbol = irClassSymbolInvoke;
        }
        return irClassSymbolCreateClassSymbol;
    }

    protected final IrClassSymbol createClassSymbol(Class declaration, IdSignature signature) {
        IrClassSymbol irClassSymbolCreatePublicClassSymbol;
        return (signature == null || (irClassSymbolCreatePublicClassSymbol = createPublicClassSymbol(declaration, signature)) == null) ? createPrivateClassSymbol(declaration) : irClassSymbolCreatePublicClassSymbol;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected IrClassSymbol createPublicClassSymbol(Class declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return new IrClassPublicSymbolImpl(signature, null, 2, 0 == true ? 1 : 0);
    }

    protected IrClassSymbol createPrivateClassSymbol(Class descriptor) {
        return new IrClassSymbolImpl(null, 1, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrConstructor declareConstructor(final Constructor declaration, Function1<? super IrConstructorSymbol, ? extends IrConstructor> constructorFactory) {
        IrConstructor irConstructorDeclareConstructor;
        Intrinsics.checkNotNullParameter(constructorFactory, "constructorFactory");
        SymbolTableSlice.Flat<Constructor, IrConstructor, IrConstructorSymbol> flat = this.constructorSlice;
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature != null) {
            irConstructorDeclareConstructor = getTable().declareConstructor(idSignatureCalculateSignature, new Function0<IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareConstructor$$inlined$declare$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrConstructorSymbol invoke() {
                    return this.createConstructorSymbol(declaration, idSignatureCalculateSignature);
                }
            }, constructorFactory);
        } else {
            Function0<IrConstructorSymbol> function0 = new Function0<IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareConstructor$$inlined$declare$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrConstructorSymbol invoke() {
                    return this.createConstructorSymbol(declaration, idSignatureCalculateSignature);
                }
            };
            synchronized (flat.getLock()) {
                IrConstructorSymbol irConstructorSymbol = flat.get(declaration);
                if (irConstructorSymbol == null) {
                    IrConstructorSymbol irConstructorSymbolInvoke = function0.invoke();
                    flat.set(declaration, irConstructorSymbolInvoke);
                    irConstructorSymbol = irConstructorSymbolInvoke;
                } else {
                    flat.getUnboundSymbols().remove(irConstructorSymbol);
                }
                irConstructorDeclareConstructor = constructorFactory.invoke(irConstructorSymbol);
                if (!irConstructorSymbol.isBound()) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (irConstructorSymbol.getOwner() != irConstructorDeclareConstructor) {
                    throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irConstructorSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irConstructorDeclareConstructor, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
            }
        }
        return irConstructorDeclareConstructor;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrConstructor declareConstructorIfNotExists(final Constructor declaration, Function1<? super IrConstructorSymbol, ? extends IrConstructor> constructorFactory) {
        IrConstructor irConstructorDeclareConstructorIfNotExists;
        Intrinsics.checkNotNullParameter(constructorFactory, "constructorFactory");
        SymbolTableSlice.Flat<Constructor, IrConstructor, IrConstructorSymbol> flat = this.constructorSlice;
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature != null) {
            irConstructorDeclareConstructorIfNotExists = getTable().declareConstructorIfNotExists(idSignatureCalculateSignature, new Function0<IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareConstructorIfNotExists$$inlined$declareIfNotExist$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrConstructorSymbol invoke() {
                    return this.createConstructorSymbol(declaration, idSignatureCalculateSignature);
                }
            }, constructorFactory);
        } else {
            Function0<IrConstructorSymbol> function0 = new Function0<IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareConstructorIfNotExists$$inlined$declareIfNotExist$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrConstructorSymbol invoke() {
                    return this.createConstructorSymbol(declaration, idSignatureCalculateSignature);
                }
            };
            synchronized (flat.getLock()) {
                IrConstructorSymbol irConstructorSymbolInvoke = flat.get(declaration);
                if (irConstructorSymbolInvoke == null) {
                    irConstructorSymbolInvoke = function0.invoke();
                    flat.set(declaration, irConstructorSymbolInvoke);
                } else if (irConstructorSymbolInvoke.isBound()) {
                    flat.getUnboundSymbols().remove(irConstructorSymbolInvoke);
                    irConstructorDeclareConstructorIfNotExists = irConstructorSymbolInvoke.getOwner();
                }
                irConstructorDeclareConstructorIfNotExists = constructorFactory.invoke(irConstructorSymbolInvoke);
                if (!irConstructorSymbolInvoke.isBound()) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (irConstructorSymbolInvoke.getOwner() != irConstructorDeclareConstructorIfNotExists) {
                    throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irConstructorSymbolInvoke.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irConstructorDeclareConstructorIfNotExists, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
            }
        }
        return irConstructorDeclareConstructorIfNotExists;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
    public IrConstructorSymbol referenceConstructor(final Constructor declaration) {
        IrConstructorSymbol irConstructorSymbolInvoke;
        IrConstructorSymbol irConstructorSymbolCreateConstructorSymbol;
        Object obj;
        SymbolTableSlice.Flat<Constructor, IrConstructor, IrConstructorSymbol> flat = this.constructorSlice;
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature == null) {
            synchronized (flat.getLock()) {
                irConstructorSymbolCreateConstructorSymbol = flat.get(declaration);
                if (irConstructorSymbolCreateConstructorSymbol == null) {
                    irConstructorSymbolCreateConstructorSymbol = createConstructorSymbol(declaration, idSignatureCalculateSignature);
                    flat.getUnboundSymbols().add(irConstructorSymbolCreateConstructorSymbol);
                    flat.set(declaration, irConstructorSymbolCreateConstructorSymbol);
                }
            }
        } else {
            SymbolTable table = getTable();
            Function0<IrConstructorSymbol> function0 = new Function0<IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$referenceConstructor$$inlined$reference$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrConstructorSymbol invoke() {
                    return this.createPublicConstructorSymbol(declaration, idSignatureCalculateSignature);
                }
            };
            Function0<IrConstructorSymbol> function1 = new Function0<IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$referenceConstructor$$inlined$reference$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrConstructorSymbol invoke() {
                    return this.createPrivateConstructorSymbol(declaration);
                }
            };
            Function0<IrConstructorSymbol> function2 = function0;
            if (idSignatureCalculateSignature.isPubliclyVisible()) {
                SymbolTableSlice.Flat flat2 = table.constructorSlice;
                synchronized (flat2.getLock()) {
                    obj = flat2.get(idSignatureCalculateSignature);
                    if (obj == null) {
                        obj = (IrBindableSymbol) ((IrConstructorSymbol) function2.invoke());
                        flat2.getUnboundSymbols().add(obj);
                        flat2.set(idSignatureCalculateSignature, obj);
                    }
                }
                irConstructorSymbolInvoke = (IrConstructorSymbol) obj;
            } else {
                irConstructorSymbolInvoke = function1.invoke();
                irConstructorSymbolInvoke.setPrivateSignature(idSignatureCalculateSignature);
            }
            irConstructorSymbolCreateConstructorSymbol = irConstructorSymbolInvoke;
        }
        return irConstructorSymbolCreateConstructorSymbol;
    }

    protected final IrConstructorSymbol createConstructorSymbol(Constructor declaration, IdSignature signature) {
        IrConstructorSymbol irConstructorSymbolCreatePublicConstructorSymbol;
        return (signature == null || (irConstructorSymbolCreatePublicConstructorSymbol = createPublicConstructorSymbol(declaration, signature)) == null) ? createPrivateConstructorSymbol(declaration) : irConstructorSymbolCreatePublicConstructorSymbol;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected IrConstructorSymbol createPublicConstructorSymbol(Constructor declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return new IrConstructorPublicSymbolImpl(signature, null, 2, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected IrConstructorSymbol createPrivateConstructorSymbol(Constructor declaration) {
        return new IrConstructorSymbolImpl(null, 1, 0 == true ? 1 : 0);
    }

    public final IrEnumEntry declareEnumEntry(final Class declaration, Function1<? super IrEnumEntrySymbol, ? extends IrEnumEntry> enumEntryFactory) {
        IrEnumEntry irEnumEntryDeclareEnumEntry;
        Intrinsics.checkNotNullParameter(enumEntryFactory, "enumEntryFactory");
        SymbolTableSlice.Flat<Class, IrEnumEntry, IrEnumEntrySymbol> flat = this.enumEntrySlice;
        final IdSignature idSignatureCalculateEnumEntrySignature = calculateEnumEntrySignature(declaration);
        if (idSignatureCalculateEnumEntrySignature != null) {
            irEnumEntryDeclareEnumEntry = getTable().declareEnumEntry(idSignatureCalculateEnumEntrySignature, new Function0<IrEnumEntrySymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareEnumEntry$$inlined$declare$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrEnumEntrySymbol invoke() {
                    return this.createEnumEntrySymbol(declaration, idSignatureCalculateEnumEntrySignature);
                }
            }, enumEntryFactory);
        } else {
            Function0<IrEnumEntrySymbol> function0 = new Function0<IrEnumEntrySymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareEnumEntry$$inlined$declare$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrEnumEntrySymbol invoke() {
                    return this.createEnumEntrySymbol(declaration, idSignatureCalculateEnumEntrySignature);
                }
            };
            synchronized (flat.getLock()) {
                IrEnumEntrySymbol irEnumEntrySymbol = flat.get(declaration);
                if (irEnumEntrySymbol == null) {
                    IrEnumEntrySymbol irEnumEntrySymbolInvoke = function0.invoke();
                    flat.set(declaration, irEnumEntrySymbolInvoke);
                    irEnumEntrySymbol = irEnumEntrySymbolInvoke;
                } else {
                    flat.getUnboundSymbols().remove(irEnumEntrySymbol);
                }
                irEnumEntryDeclareEnumEntry = enumEntryFactory.invoke(irEnumEntrySymbol);
                if (!irEnumEntrySymbol.isBound()) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (irEnumEntrySymbol.getOwner() != irEnumEntryDeclareEnumEntry) {
                    throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irEnumEntrySymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irEnumEntryDeclareEnumEntry, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
            }
        }
        return irEnumEntryDeclareEnumEntry;
    }

    public final IrEnumEntry declareEnumEntry(final int startOffset, final int endOffset, final IrDeclarationOrigin origin, final Class declaration) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        return declareEnumEntry(declaration, new Function1<IrEnumEntrySymbol, IrEnumEntry>(this) { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension.declareEnumEntry.4
            final /* synthetic */ SymbolTableExtension<Declaration, Class, TypeAlias, Script, Function, Constructor, Property, ValueParameter, TypeParameter> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrEnumEntry invoke(IrEnumEntrySymbol enumEntrySymbol) {
                Intrinsics.checkNotNullParameter(enumEntrySymbol, "enumEntrySymbol");
                return this.this$0.defaultEnumEntryFactory(startOffset, endOffset, origin, declaration, enumEntrySymbol);
            }
        });
    }

    public final IrEnumEntry declareEnumEntryIfNotExists(final Class declaration, Function1<? super IrEnumEntrySymbol, ? extends IrEnumEntry> enumEntryFactory) {
        IrEnumEntry irEnumEntryDeclareEnumEntryIfNotExists;
        Intrinsics.checkNotNullParameter(enumEntryFactory, "enumEntryFactory");
        SymbolTableSlice.Flat<Class, IrEnumEntry, IrEnumEntrySymbol> flat = this.enumEntrySlice;
        final IdSignature idSignatureCalculateEnumEntrySignature = calculateEnumEntrySignature(declaration);
        if (idSignatureCalculateEnumEntrySignature != null) {
            irEnumEntryDeclareEnumEntryIfNotExists = getTable().declareEnumEntryIfNotExists(idSignatureCalculateEnumEntrySignature, new Function0<IrEnumEntrySymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareEnumEntryIfNotExists$$inlined$declareIfNotExist$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrEnumEntrySymbol invoke() {
                    return this.createEnumEntrySymbol(declaration, idSignatureCalculateEnumEntrySignature);
                }
            }, enumEntryFactory);
        } else {
            Function0<IrEnumEntrySymbol> function0 = new Function0<IrEnumEntrySymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareEnumEntryIfNotExists$$inlined$declareIfNotExist$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrEnumEntrySymbol invoke() {
                    return this.createEnumEntrySymbol(declaration, idSignatureCalculateEnumEntrySignature);
                }
            };
            synchronized (flat.getLock()) {
                IrEnumEntrySymbol irEnumEntrySymbolInvoke = flat.get(declaration);
                if (irEnumEntrySymbolInvoke == null) {
                    irEnumEntrySymbolInvoke = function0.invoke();
                    flat.set(declaration, irEnumEntrySymbolInvoke);
                } else if (irEnumEntrySymbolInvoke.isBound()) {
                    flat.getUnboundSymbols().remove(irEnumEntrySymbolInvoke);
                    irEnumEntryDeclareEnumEntryIfNotExists = irEnumEntrySymbolInvoke.getOwner();
                }
                irEnumEntryDeclareEnumEntryIfNotExists = enumEntryFactory.invoke(irEnumEntrySymbolInvoke);
                if (!irEnumEntrySymbolInvoke.isBound()) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (irEnumEntrySymbolInvoke.getOwner() != irEnumEntryDeclareEnumEntryIfNotExists) {
                    throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irEnumEntrySymbolInvoke.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irEnumEntryDeclareEnumEntryIfNotExists, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
            }
        }
        return irEnumEntryDeclareEnumEntryIfNotExists;
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
    public IrEnumEntrySymbol referenceEnumEntry(final Class declaration) {
        IrEnumEntrySymbol irEnumEntrySymbolInvoke;
        IrEnumEntrySymbol irEnumEntrySymbolCreateEnumEntrySymbol;
        Object obj;
        SymbolTableSlice.Flat<Class, IrEnumEntry, IrEnumEntrySymbol> flat = this.enumEntrySlice;
        final IdSignature idSignatureCalculateEnumEntrySignature = calculateEnumEntrySignature(declaration);
        if (idSignatureCalculateEnumEntrySignature == null) {
            synchronized (flat.getLock()) {
                irEnumEntrySymbolCreateEnumEntrySymbol = flat.get(declaration);
                if (irEnumEntrySymbolCreateEnumEntrySymbol == null) {
                    irEnumEntrySymbolCreateEnumEntrySymbol = createEnumEntrySymbol(declaration, idSignatureCalculateEnumEntrySignature);
                    flat.getUnboundSymbols().add(irEnumEntrySymbolCreateEnumEntrySymbol);
                    flat.set(declaration, irEnumEntrySymbolCreateEnumEntrySymbol);
                }
            }
        } else {
            SymbolTable table = getTable();
            Function0<IrEnumEntrySymbol> function0 = new Function0<IrEnumEntrySymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$referenceEnumEntry$$inlined$reference$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrEnumEntrySymbol invoke() {
                    return this.createPublicEnumEntrySymbol(declaration, idSignatureCalculateEnumEntrySignature);
                }
            };
            Function0<IrEnumEntrySymbol> function1 = new Function0<IrEnumEntrySymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$referenceEnumEntry$$inlined$reference$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrEnumEntrySymbol invoke() {
                    return this.createPrivateEnumEntrySymbol(declaration);
                }
            };
            Function0<IrEnumEntrySymbol> function2 = function0;
            if (idSignatureCalculateEnumEntrySignature.isPubliclyVisible()) {
                SymbolTableSlice.Flat flat2 = table.enumEntrySlice;
                synchronized (flat2.getLock()) {
                    obj = flat2.get(idSignatureCalculateEnumEntrySignature);
                    if (obj == null) {
                        obj = (IrBindableSymbol) ((IrEnumEntrySymbol) function2.invoke());
                        flat2.getUnboundSymbols().add(obj);
                        flat2.set(idSignatureCalculateEnumEntrySignature, obj);
                    }
                }
                irEnumEntrySymbolInvoke = (IrEnumEntrySymbol) obj;
            } else {
                irEnumEntrySymbolInvoke = function1.invoke();
                irEnumEntrySymbolInvoke.setPrivateSignature(idSignatureCalculateEnumEntrySignature);
            }
            irEnumEntrySymbolCreateEnumEntrySymbol = irEnumEntrySymbolInvoke;
        }
        return irEnumEntrySymbolCreateEnumEntrySymbol;
    }

    protected final IrEnumEntrySymbol createEnumEntrySymbol(Class declaration, IdSignature signature) {
        IrEnumEntrySymbol irEnumEntrySymbolCreatePublicEnumEntrySymbol;
        return (signature == null || (irEnumEntrySymbolCreatePublicEnumEntrySymbol = createPublicEnumEntrySymbol(declaration, signature)) == null) ? createPrivateEnumEntrySymbol(declaration) : irEnumEntrySymbolCreatePublicEnumEntrySymbol;
    }

    protected IrEnumEntrySymbol createPublicEnumEntrySymbol(Class declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return new IrEnumEntryPublicSymbolImpl(signature, null, 2, null);
    }

    protected IrEnumEntrySymbol createPrivateEnumEntrySymbol(Class declaration) {
        return new IrEnumEntrySymbolImpl(null, 1, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IrField declareField$default(SymbolTableExtension symbolTableExtension, final int i, final int i2, final IrDeclarationOrigin irDeclarationOrigin, final Object obj, final IrType irType, DescriptorVisibility descriptorVisibility, Function1 function1, int i3, Object obj2) {
        Function1 function2;
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: declareField");
        }
        DescriptorVisibility descriptorVisibility2 = (i3 & 32) != 0 ? null : descriptorVisibility;
        if ((i3 & 64) != 0) {
            final DescriptorVisibility descriptorVisibility3 = descriptorVisibility2;
            function2 = new Function1<IrFieldSymbol, IrField>(symbolTableExtension) { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension.declareField.1
                final /* synthetic */ SymbolTableExtension<Declaration, Class, TypeAlias, Script, Function, Constructor, Property, ValueParameter, TypeParameter> this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                    this.this$0 = symbolTableExtension;
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrField invoke(IrFieldSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.this$0.defaultFieldFactory(i, i2, irDeclarationOrigin, obj, irType, descriptorVisibility3, it);
                }
            };
        } else {
            function2 = function1;
        }
        return symbolTableExtension.declareField(i, i2, irDeclarationOrigin, obj, irType, descriptorVisibility2, function2);
    }

    public final IrField declareField(int startOffset, int endOffset, IrDeclarationOrigin origin, final Property declaration, IrType type, DescriptorVisibility visibility, Function1<? super IrFieldSymbol, ? extends IrField> fieldFactory) {
        IrField irFieldDeclareField;
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(fieldFactory, "fieldFactory");
        SymbolTableSlice.Flat<Property, IrField, IrFieldSymbol> flat = this.fieldSlice;
        final IdSignature idSignatureCalculateFieldSignature = calculateFieldSignature(declaration);
        if (idSignatureCalculateFieldSignature != null) {
            irFieldDeclareField = getTable().declareField(idSignatureCalculateFieldSignature, new Function0<IrFieldSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareField$$inlined$declare$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrFieldSymbol invoke() {
                    return this.createFieldSymbol(declaration, idSignatureCalculateFieldSignature);
                }
            }, fieldFactory);
        } else {
            Function0<IrFieldSymbol> function0 = new Function0<IrFieldSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareField$$inlined$declare$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrFieldSymbol invoke() {
                    return this.createFieldSymbol(declaration, idSignatureCalculateFieldSignature);
                }
            };
            synchronized (flat.getLock()) {
                IrFieldSymbol irFieldSymbol = flat.get(declaration);
                if (irFieldSymbol == null) {
                    IrFieldSymbol irFieldSymbolInvoke = function0.invoke();
                    flat.set(declaration, irFieldSymbolInvoke);
                    irFieldSymbol = irFieldSymbolInvoke;
                } else {
                    flat.getUnboundSymbols().remove(irFieldSymbol);
                }
                irFieldDeclareField = fieldFactory.invoke(irFieldSymbol);
                if (!irFieldSymbol.isBound()) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (irFieldSymbol.getOwner() != irFieldDeclareField) {
                    throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irFieldSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irFieldDeclareField, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
            }
        }
        return irFieldDeclareField;
    }

    public final IrField declareField(int startOffset, int endOffset, IrDeclarationOrigin origin, Property declaration, IrType type, IrExpressionBody irInitializer) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(type, "type");
        IrField irFieldDeclareField$default = declareField$default(this, startOffset, endOffset, origin, declaration, type, null, null, 96, null);
        irFieldDeclareField$default.setInitializer(irInitializer);
        return irFieldDeclareField$default;
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
    public IrFieldSymbol referenceField(final Property declaration) {
        IrFieldSymbol irFieldSymbolInvoke;
        IrFieldSymbol irFieldSymbolCreateFieldSymbol;
        Object obj;
        SymbolTableSlice.Flat<Property, IrField, IrFieldSymbol> flat = this.fieldSlice;
        final IdSignature idSignatureCalculateFieldSignature = calculateFieldSignature(declaration);
        if (idSignatureCalculateFieldSignature == null) {
            synchronized (flat.getLock()) {
                irFieldSymbolCreateFieldSymbol = flat.get(declaration);
                if (irFieldSymbolCreateFieldSymbol == null) {
                    irFieldSymbolCreateFieldSymbol = createFieldSymbol(declaration, idSignatureCalculateFieldSignature);
                    flat.getUnboundSymbols().add(irFieldSymbolCreateFieldSymbol);
                    flat.set(declaration, irFieldSymbolCreateFieldSymbol);
                }
            }
        } else {
            SymbolTable table = getTable();
            Function0<IrFieldSymbol> function0 = new Function0<IrFieldSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$referenceField$$inlined$reference$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrFieldSymbol invoke() {
                    return this.createPublicFieldSymbol(declaration, idSignatureCalculateFieldSignature);
                }
            };
            Function0<IrFieldSymbol> function1 = new Function0<IrFieldSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$referenceField$$inlined$reference$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrFieldSymbol invoke() {
                    return this.createPrivateFieldSymbol(declaration);
                }
            };
            Function0<IrFieldSymbol> function2 = function0;
            if (idSignatureCalculateFieldSignature.isPubliclyVisible()) {
                SymbolTableSlice.Flat flat2 = table.fieldSlice;
                synchronized (flat2.getLock()) {
                    obj = flat2.get(idSignatureCalculateFieldSignature);
                    if (obj == null) {
                        obj = (IrBindableSymbol) ((IrFieldSymbol) function2.invoke());
                        flat2.getUnboundSymbols().add(obj);
                        flat2.set(idSignatureCalculateFieldSignature, obj);
                    }
                }
                irFieldSymbolInvoke = (IrFieldSymbol) obj;
            } else {
                irFieldSymbolInvoke = function1.invoke();
                irFieldSymbolInvoke.setPrivateSignature(idSignatureCalculateFieldSignature);
            }
            irFieldSymbolCreateFieldSymbol = irFieldSymbolInvoke;
        }
        return irFieldSymbolCreateFieldSymbol;
    }

    protected final IrFieldSymbol createFieldSymbol(Property declaration, IdSignature signature) {
        IrFieldSymbol irFieldSymbolCreatePublicFieldSymbol;
        return (signature == null || (irFieldSymbolCreatePublicFieldSymbol = createPublicFieldSymbol(declaration, signature)) == null) ? createPrivateFieldSymbol(declaration) : irFieldSymbolCreatePublicFieldSymbol;
    }

    protected IrFieldSymbol createPublicFieldSymbol(Property declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return new IrFieldPublicSymbolImpl(signature, null, 2, null);
    }

    protected IrFieldSymbol createPrivateFieldSymbol(Property declaration) {
        return new IrFieldSymbolImpl(null, 1, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrProperty declareProperty(final Property declaration, Function1<? super IrPropertySymbol, ? extends IrProperty> propertyFactory) {
        IrProperty irPropertyDeclareProperty;
        Intrinsics.checkNotNullParameter(propertyFactory, "propertyFactory");
        SymbolTableSlice.Flat<Property, IrProperty, IrPropertySymbol> flat = this.propertySlice;
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature != null) {
            irPropertyDeclareProperty = getTable().declareProperty(idSignatureCalculateSignature, new Function0<IrPropertySymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareProperty$$inlined$declare$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrPropertySymbol invoke() {
                    return this.createPropertySymbol(declaration, idSignatureCalculateSignature);
                }
            }, propertyFactory);
        } else {
            Function0<IrPropertySymbol> function0 = new Function0<IrPropertySymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareProperty$$inlined$declare$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrPropertySymbol invoke() {
                    return this.createPropertySymbol(declaration, idSignatureCalculateSignature);
                }
            };
            synchronized (flat.getLock()) {
                IrPropertySymbol irPropertySymbol = flat.get(declaration);
                if (irPropertySymbol == null) {
                    IrPropertySymbol irPropertySymbolInvoke = function0.invoke();
                    flat.set(declaration, irPropertySymbolInvoke);
                    irPropertySymbol = irPropertySymbolInvoke;
                } else {
                    flat.getUnboundSymbols().remove(irPropertySymbol);
                }
                irPropertyDeclareProperty = propertyFactory.invoke(irPropertySymbol);
                if (!irPropertySymbol.isBound()) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (irPropertySymbol.getOwner() != irPropertyDeclareProperty) {
                    throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irPropertySymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irPropertyDeclareProperty, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
            }
        }
        return irPropertyDeclareProperty;
    }

    public final IrProperty declareProperty(final int startOffset, final int endOffset, final IrDeclarationOrigin origin, final Property declaration, final boolean isDelegated) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        return declareProperty(declaration, new Function1<IrPropertySymbol, IrProperty>(this) { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension.declareProperty.3
            final /* synthetic */ SymbolTableExtension<Declaration, Class, TypeAlias, Script, Function, Constructor, Property, ValueParameter, TypeParameter> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrProperty invoke(IrPropertySymbol propertySymbol) {
                Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
                return this.this$0.defaultPropertyFactory(startOffset, endOffset, origin, declaration, isDelegated, propertySymbol);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrProperty declarePropertyIfNotExists(final Property declaration, Function1<? super IrPropertySymbol, ? extends IrProperty> propertyFactory) {
        IrProperty irPropertyDeclarePropertyIfNotExists;
        Intrinsics.checkNotNullParameter(propertyFactory, "propertyFactory");
        SymbolTableSlice.Flat<Property, IrProperty, IrPropertySymbol> flat = this.propertySlice;
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature != null) {
            irPropertyDeclarePropertyIfNotExists = getTable().declarePropertyIfNotExists(idSignatureCalculateSignature, new Function0<IrPropertySymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declarePropertyIfNotExists$$inlined$declareIfNotExist$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrPropertySymbol invoke() {
                    return this.createPropertySymbol(declaration, idSignatureCalculateSignature);
                }
            }, propertyFactory);
        } else {
            Function0<IrPropertySymbol> function0 = new Function0<IrPropertySymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declarePropertyIfNotExists$$inlined$declareIfNotExist$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrPropertySymbol invoke() {
                    return this.createPropertySymbol(declaration, idSignatureCalculateSignature);
                }
            };
            synchronized (flat.getLock()) {
                IrPropertySymbol irPropertySymbolInvoke = flat.get(declaration);
                if (irPropertySymbolInvoke == null) {
                    irPropertySymbolInvoke = function0.invoke();
                    flat.set(declaration, irPropertySymbolInvoke);
                } else if (irPropertySymbolInvoke.isBound()) {
                    flat.getUnboundSymbols().remove(irPropertySymbolInvoke);
                    irPropertyDeclarePropertyIfNotExists = irPropertySymbolInvoke.getOwner();
                }
                irPropertyDeclarePropertyIfNotExists = propertyFactory.invoke(irPropertySymbolInvoke);
                if (!irPropertySymbolInvoke.isBound()) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (irPropertySymbolInvoke.getOwner() != irPropertyDeclarePropertyIfNotExists) {
                    throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irPropertySymbolInvoke.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irPropertyDeclarePropertyIfNotExists, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
            }
        }
        return irPropertyDeclarePropertyIfNotExists;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
    public IrPropertySymbol referenceProperty(final Property declaration) {
        IrPropertySymbol irPropertySymbolInvoke;
        IrPropertySymbol irPropertySymbolCreatePropertySymbol;
        Object obj;
        SymbolTableSlice.Flat<Property, IrProperty, IrPropertySymbol> flat = this.propertySlice;
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature == null) {
            synchronized (flat.getLock()) {
                irPropertySymbolCreatePropertySymbol = flat.get(declaration);
                if (irPropertySymbolCreatePropertySymbol == null) {
                    irPropertySymbolCreatePropertySymbol = createPropertySymbol(declaration, idSignatureCalculateSignature);
                    flat.getUnboundSymbols().add(irPropertySymbolCreatePropertySymbol);
                    flat.set(declaration, irPropertySymbolCreatePropertySymbol);
                }
            }
        } else {
            SymbolTable table = getTable();
            Function0<IrPropertySymbol> function0 = new Function0<IrPropertySymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$referenceProperty$$inlined$reference$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrPropertySymbol invoke() {
                    return this.createPublicPropertySymbol(declaration, idSignatureCalculateSignature);
                }
            };
            Function0<IrPropertySymbol> function1 = new Function0<IrPropertySymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$referenceProperty$$inlined$reference$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrPropertySymbol invoke() {
                    return this.createPrivatePropertySymbol(declaration);
                }
            };
            Function0<IrPropertySymbol> function2 = function0;
            if (idSignatureCalculateSignature.isPubliclyVisible()) {
                SymbolTableSlice.Flat flat2 = table.propertySlice;
                synchronized (flat2.getLock()) {
                    obj = flat2.get(idSignatureCalculateSignature);
                    if (obj == null) {
                        obj = (IrBindableSymbol) ((IrPropertySymbol) function2.invoke());
                        flat2.getUnboundSymbols().add(obj);
                        flat2.set(idSignatureCalculateSignature, obj);
                    }
                }
                irPropertySymbolInvoke = (IrPropertySymbol) obj;
            } else {
                irPropertySymbolInvoke = function1.invoke();
                irPropertySymbolInvoke.setPrivateSignature(idSignatureCalculateSignature);
            }
            irPropertySymbolCreatePropertySymbol = irPropertySymbolInvoke;
        }
        return irPropertySymbolCreatePropertySymbol;
    }

    protected final IrPropertySymbol createPropertySymbol(Property declaration, IdSignature signature) {
        IrPropertySymbol irPropertySymbolCreatePublicPropertySymbol;
        return (signature == null || (irPropertySymbolCreatePublicPropertySymbol = createPublicPropertySymbol(declaration, signature)) == null) ? createPrivatePropertySymbol(declaration) : irPropertySymbolCreatePublicPropertySymbol;
    }

    protected IrPropertySymbol createPublicPropertySymbol(Property declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return new IrPropertyPublicSymbolImpl(signature, null, 2, null);
    }

    protected IrPropertySymbol createPrivatePropertySymbol(Property declaration) {
        return new IrPropertySymbolImpl(null, 1, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrTypeAlias declareTypeAlias(final TypeAlias declaration, Function1<? super IrTypeAliasSymbol, ? extends IrTypeAlias> typeAliasFactory) {
        IrTypeAlias irTypeAliasDeclareTypeAlias;
        Intrinsics.checkNotNullParameter(typeAliasFactory, "typeAliasFactory");
        SymbolTableSlice.Flat<TypeAlias, IrTypeAlias, IrTypeAliasSymbol> flat = this.typeAliasSlice;
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature != null) {
            irTypeAliasDeclareTypeAlias = getTable().declareTypeAlias(idSignatureCalculateSignature, new Function0<IrTypeAliasSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareTypeAlias$$inlined$declare$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrTypeAliasSymbol invoke() {
                    return this.createTypeAliasSymbol(declaration, idSignatureCalculateSignature);
                }
            }, typeAliasFactory);
        } else {
            Function0<IrTypeAliasSymbol> function0 = new Function0<IrTypeAliasSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareTypeAlias$$inlined$declare$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrTypeAliasSymbol invoke() {
                    return this.createTypeAliasSymbol(declaration, idSignatureCalculateSignature);
                }
            };
            synchronized (flat.getLock()) {
                IrTypeAliasSymbol irTypeAliasSymbol = flat.get(declaration);
                if (irTypeAliasSymbol == null) {
                    IrTypeAliasSymbol irTypeAliasSymbolInvoke = function0.invoke();
                    flat.set(declaration, irTypeAliasSymbolInvoke);
                    irTypeAliasSymbol = irTypeAliasSymbolInvoke;
                } else {
                    flat.getUnboundSymbols().remove(irTypeAliasSymbol);
                }
                irTypeAliasDeclareTypeAlias = typeAliasFactory.invoke(irTypeAliasSymbol);
                if (!irTypeAliasSymbol.isBound()) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (irTypeAliasSymbol.getOwner() != irTypeAliasDeclareTypeAlias) {
                    throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irTypeAliasSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irTypeAliasDeclareTypeAlias, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
            }
        }
        return irTypeAliasDeclareTypeAlias;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrTypeAlias declareTypeAliasIfNotExists(final TypeAlias declaration, Function1<? super IrTypeAliasSymbol, ? extends IrTypeAlias> typeAliasFactory) {
        IrTypeAlias irTypeAliasDeclareTypeAliasIfNotExists;
        Intrinsics.checkNotNullParameter(typeAliasFactory, "typeAliasFactory");
        SymbolTableSlice.Flat<TypeAlias, IrTypeAlias, IrTypeAliasSymbol> flat = this.typeAliasSlice;
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature != null) {
            irTypeAliasDeclareTypeAliasIfNotExists = getTable().declareTypeAliasIfNotExists(idSignatureCalculateSignature, new Function0<IrTypeAliasSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareTypeAliasIfNotExists$$inlined$declareIfNotExist$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrTypeAliasSymbol invoke() {
                    return this.createTypeAliasSymbol(declaration, idSignatureCalculateSignature);
                }
            }, typeAliasFactory);
        } else {
            Function0<IrTypeAliasSymbol> function0 = new Function0<IrTypeAliasSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareTypeAliasIfNotExists$$inlined$declareIfNotExist$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrTypeAliasSymbol invoke() {
                    return this.createTypeAliasSymbol(declaration, idSignatureCalculateSignature);
                }
            };
            synchronized (flat.getLock()) {
                IrTypeAliasSymbol irTypeAliasSymbolInvoke = flat.get(declaration);
                if (irTypeAliasSymbolInvoke == null) {
                    irTypeAliasSymbolInvoke = function0.invoke();
                    flat.set(declaration, irTypeAliasSymbolInvoke);
                } else if (irTypeAliasSymbolInvoke.isBound()) {
                    flat.getUnboundSymbols().remove(irTypeAliasSymbolInvoke);
                    irTypeAliasDeclareTypeAliasIfNotExists = irTypeAliasSymbolInvoke.getOwner();
                }
                irTypeAliasDeclareTypeAliasIfNotExists = typeAliasFactory.invoke(irTypeAliasSymbolInvoke);
                if (!irTypeAliasSymbolInvoke.isBound()) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (irTypeAliasSymbolInvoke.getOwner() != irTypeAliasDeclareTypeAliasIfNotExists) {
                    throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irTypeAliasSymbolInvoke.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irTypeAliasDeclareTypeAliasIfNotExists, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
            }
        }
        return irTypeAliasDeclareTypeAliasIfNotExists;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
    public IrTypeAliasSymbol referenceTypeAlias(final TypeAlias declaration) {
        IrTypeAliasSymbol irTypeAliasSymbolInvoke;
        IrTypeAliasSymbol irTypeAliasSymbolCreateTypeAliasSymbol;
        Object obj;
        SymbolTableSlice.Flat<TypeAlias, IrTypeAlias, IrTypeAliasSymbol> flat = this.typeAliasSlice;
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature == null) {
            synchronized (flat.getLock()) {
                irTypeAliasSymbolCreateTypeAliasSymbol = flat.get(declaration);
                if (irTypeAliasSymbolCreateTypeAliasSymbol == null) {
                    irTypeAliasSymbolCreateTypeAliasSymbol = createTypeAliasSymbol(declaration, idSignatureCalculateSignature);
                    flat.getUnboundSymbols().add(irTypeAliasSymbolCreateTypeAliasSymbol);
                    flat.set(declaration, irTypeAliasSymbolCreateTypeAliasSymbol);
                }
            }
        } else {
            SymbolTable table = getTable();
            Function0<IrTypeAliasSymbol> function0 = new Function0<IrTypeAliasSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$referenceTypeAlias$$inlined$reference$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrTypeAliasSymbol invoke() {
                    return this.createPublicTypeAliasSymbol(declaration, idSignatureCalculateSignature);
                }
            };
            Function0<IrTypeAliasSymbol> function1 = new Function0<IrTypeAliasSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$referenceTypeAlias$$inlined$reference$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrTypeAliasSymbol invoke() {
                    return this.createPrivateTypeAliasSymbol(declaration);
                }
            };
            Function0<IrTypeAliasSymbol> function2 = function0;
            if (idSignatureCalculateSignature.isPubliclyVisible()) {
                SymbolTableSlice.Flat flat2 = table.typeAliasSlice;
                synchronized (flat2.getLock()) {
                    obj = flat2.get(idSignatureCalculateSignature);
                    if (obj == null) {
                        obj = (IrBindableSymbol) ((IrTypeAliasSymbol) function2.invoke());
                        flat2.getUnboundSymbols().add(obj);
                        flat2.set(idSignatureCalculateSignature, obj);
                    }
                }
                irTypeAliasSymbolInvoke = (IrTypeAliasSymbol) obj;
            } else {
                irTypeAliasSymbolInvoke = function1.invoke();
                irTypeAliasSymbolInvoke.setPrivateSignature(idSignatureCalculateSignature);
            }
            irTypeAliasSymbolCreateTypeAliasSymbol = irTypeAliasSymbolInvoke;
        }
        return irTypeAliasSymbolCreateTypeAliasSymbol;
    }

    protected final IrTypeAliasSymbol createTypeAliasSymbol(TypeAlias declaration, IdSignature signature) {
        IrTypeAliasSymbol irTypeAliasSymbolCreatePublicTypeAliasSymbol;
        return (signature == null || (irTypeAliasSymbolCreatePublicTypeAliasSymbol = createPublicTypeAliasSymbol(declaration, signature)) == null) ? createPrivateTypeAliasSymbol(declaration) : irTypeAliasSymbolCreatePublicTypeAliasSymbol;
    }

    protected IrTypeAliasSymbol createPublicTypeAliasSymbol(TypeAlias declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return new IrTypeAliasPublicSymbolImpl(signature, null, 2, null);
    }

    protected IrTypeAliasSymbol createPrivateTypeAliasSymbol(TypeAlias declaration) {
        return new IrTypeAliasSymbolImpl(null, 1, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrSimpleFunction declareSimpleFunction(final Function declaration, Function1<? super IrSimpleFunctionSymbol, ? extends IrSimpleFunction> functionFactory) {
        IrSimpleFunction irSimpleFunctionDeclareSimpleFunction;
        Intrinsics.checkNotNullParameter(functionFactory, "functionFactory");
        SymbolTableSlice.Flat<Function, IrSimpleFunction, IrSimpleFunctionSymbol> flat = this.functionSlice;
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature != null) {
            irSimpleFunctionDeclareSimpleFunction = getTable().declareSimpleFunction(idSignatureCalculateSignature, new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareSimpleFunction$$inlined$declare$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrSimpleFunctionSymbol invoke() {
                    return this.createFunctionSymbol(declaration, idSignatureCalculateSignature);
                }
            }, functionFactory);
        } else {
            Function0<IrSimpleFunctionSymbol> function0 = new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareSimpleFunction$$inlined$declare$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrSimpleFunctionSymbol invoke() {
                    return this.createFunctionSymbol(declaration, idSignatureCalculateSignature);
                }
            };
            synchronized (flat.getLock()) {
                IrSimpleFunctionSymbol irSimpleFunctionSymbol = flat.get(declaration);
                if (irSimpleFunctionSymbol == null) {
                    IrSimpleFunctionSymbol irSimpleFunctionSymbolInvoke = function0.invoke();
                    flat.set(declaration, irSimpleFunctionSymbolInvoke);
                    irSimpleFunctionSymbol = irSimpleFunctionSymbolInvoke;
                } else {
                    flat.getUnboundSymbols().remove(irSimpleFunctionSymbol);
                }
                irSimpleFunctionDeclareSimpleFunction = functionFactory.invoke(irSimpleFunctionSymbol);
                if (!irSimpleFunctionSymbol.isBound()) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (irSimpleFunctionSymbol.getOwner() != irSimpleFunctionDeclareSimpleFunction) {
                    throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irSimpleFunctionSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irSimpleFunctionDeclareSimpleFunction, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
            }
        }
        return irSimpleFunctionDeclareSimpleFunction;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrSimpleFunction declareSimpleFunctionIfNotExists(final Function declaration, Function1<? super IrSimpleFunctionSymbol, ? extends IrSimpleFunction> functionFactory) {
        IrSimpleFunction irSimpleFunctionDeclareSimpleFunctionIfNotExists;
        Intrinsics.checkNotNullParameter(functionFactory, "functionFactory");
        SymbolTableSlice.Flat<Function, IrSimpleFunction, IrSimpleFunctionSymbol> flat = this.functionSlice;
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature != null) {
            irSimpleFunctionDeclareSimpleFunctionIfNotExists = getTable().declareSimpleFunctionIfNotExists(idSignatureCalculateSignature, new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareSimpleFunctionIfNotExists$$inlined$declareIfNotExist$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrSimpleFunctionSymbol invoke() {
                    return this.createFunctionSymbol(declaration, idSignatureCalculateSignature);
                }
            }, functionFactory);
        } else {
            Function0<IrSimpleFunctionSymbol> function0 = new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareSimpleFunctionIfNotExists$$inlined$declareIfNotExist$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrSimpleFunctionSymbol invoke() {
                    return this.createFunctionSymbol(declaration, idSignatureCalculateSignature);
                }
            };
            synchronized (flat.getLock()) {
                IrSimpleFunctionSymbol irSimpleFunctionSymbolInvoke = flat.get(declaration);
                if (irSimpleFunctionSymbolInvoke == null) {
                    irSimpleFunctionSymbolInvoke = function0.invoke();
                    flat.set(declaration, irSimpleFunctionSymbolInvoke);
                } else if (irSimpleFunctionSymbolInvoke.isBound()) {
                    flat.getUnboundSymbols().remove(irSimpleFunctionSymbolInvoke);
                    irSimpleFunctionDeclareSimpleFunctionIfNotExists = irSimpleFunctionSymbolInvoke.getOwner();
                }
                irSimpleFunctionDeclareSimpleFunctionIfNotExists = functionFactory.invoke(irSimpleFunctionSymbolInvoke);
                if (!irSimpleFunctionSymbolInvoke.isBound()) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (irSimpleFunctionSymbolInvoke.getOwner() != irSimpleFunctionDeclareSimpleFunctionIfNotExists) {
                    throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irSimpleFunctionSymbolInvoke.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irSimpleFunctionDeclareSimpleFunctionIfNotExists, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
            }
        }
        return irSimpleFunctionDeclareSimpleFunctionIfNotExists;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
    public IrSimpleFunctionSymbol referenceSimpleFunction(final Function declaration) {
        IrSimpleFunctionSymbol irSimpleFunctionSymbolInvoke;
        IrSimpleFunctionSymbol irSimpleFunctionSymbolCreateFunctionSymbol;
        Object obj;
        SymbolTableSlice.Flat<Function, IrSimpleFunction, IrSimpleFunctionSymbol> flat = this.functionSlice;
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature == null) {
            synchronized (flat.getLock()) {
                irSimpleFunctionSymbolCreateFunctionSymbol = flat.get(declaration);
                if (irSimpleFunctionSymbolCreateFunctionSymbol == null) {
                    irSimpleFunctionSymbolCreateFunctionSymbol = createFunctionSymbol(declaration, idSignatureCalculateSignature);
                    flat.getUnboundSymbols().add(irSimpleFunctionSymbolCreateFunctionSymbol);
                    flat.set(declaration, irSimpleFunctionSymbolCreateFunctionSymbol);
                }
            }
        } else {
            SymbolTable table = getTable();
            Function0<IrSimpleFunctionSymbol> function0 = new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$referenceSimpleFunction$$inlined$reference$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrSimpleFunctionSymbol invoke() {
                    return this.createPublicFunctionSymbol(declaration, idSignatureCalculateSignature);
                }
            };
            Function0<IrSimpleFunctionSymbol> function1 = new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$referenceSimpleFunction$$inlined$reference$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrSimpleFunctionSymbol invoke() {
                    return this.createPrivateFunctionSymbol(declaration);
                }
            };
            Function0<IrSimpleFunctionSymbol> function2 = function0;
            if (idSignatureCalculateSignature.isPubliclyVisible()) {
                SymbolTableSlice.Flat flat2 = table.functionSlice;
                synchronized (flat2.getLock()) {
                    obj = flat2.get(idSignatureCalculateSignature);
                    if (obj == null) {
                        obj = (IrBindableSymbol) ((IrSimpleFunctionSymbol) function2.invoke());
                        flat2.getUnboundSymbols().add(obj);
                        flat2.set(idSignatureCalculateSignature, obj);
                    }
                }
                irSimpleFunctionSymbolInvoke = (IrSimpleFunctionSymbol) obj;
            } else {
                irSimpleFunctionSymbolInvoke = function1.invoke();
                irSimpleFunctionSymbolInvoke.setPrivateSignature(idSignatureCalculateSignature);
            }
            irSimpleFunctionSymbolCreateFunctionSymbol = irSimpleFunctionSymbolInvoke;
        }
        return irSimpleFunctionSymbolCreateFunctionSymbol;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <Function extends Declaration, Declaration> Void referenceDeclaredFunction$throwError(Function function) {
        throw new IllegalStateException(("Function is not declared: " + function).toString());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
    public IrSimpleFunctionSymbol referenceDeclaredFunction(final Function declaration) {
        IrSimpleFunctionSymbol irSimpleFunctionSymbolInvoke;
        IrSimpleFunctionSymbol irSimpleFunctionSymbol;
        Object obj;
        SymbolTableSlice.Flat<Function, IrSimpleFunction, IrSimpleFunctionSymbol> flat = this.functionSlice;
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature == null) {
            synchronized (flat.getLock()) {
                irSimpleFunctionSymbol = flat.get(declaration);
                if (irSimpleFunctionSymbol == null) {
                    referenceDeclaredFunction$throwError(declaration);
                    throw null;
                }
            }
        } else {
            SymbolTable table = getTable();
            Function0<IrSimpleFunctionSymbol> function0 = new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$referenceDeclaredFunction$$inlined$reference$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrSimpleFunctionSymbol invoke() {
                    SymbolTableExtension.referenceDeclaredFunction$throwError(declaration);
                    throw null;
                }
            };
            Function0<IrSimpleFunctionSymbol> function1 = new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$referenceDeclaredFunction$$inlined$reference$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrSimpleFunctionSymbol invoke() {
                    SymbolTableExtension.referenceDeclaredFunction$throwError(declaration);
                    throw null;
                }
            };
            Function0<IrSimpleFunctionSymbol> function2 = function0;
            if (idSignatureCalculateSignature.isPubliclyVisible()) {
                SymbolTableSlice.Flat flat2 = table.functionSlice;
                synchronized (flat2.getLock()) {
                    obj = flat2.get(idSignatureCalculateSignature);
                    if (obj == null) {
                        obj = (IrBindableSymbol) ((IrSimpleFunctionSymbol) function2.invoke());
                        flat2.getUnboundSymbols().add(obj);
                        flat2.set(idSignatureCalculateSignature, obj);
                    }
                }
                irSimpleFunctionSymbolInvoke = (IrSimpleFunctionSymbol) obj;
            } else {
                irSimpleFunctionSymbolInvoke = function1.invoke();
                irSimpleFunctionSymbolInvoke.setPrivateSignature(idSignatureCalculateSignature);
            }
            irSimpleFunctionSymbol = irSimpleFunctionSymbolInvoke;
        }
        return irSimpleFunctionSymbol;
    }

    protected final IrSimpleFunctionSymbol createFunctionSymbol(Function declaration, IdSignature signature) {
        IrSimpleFunctionSymbol irSimpleFunctionSymbolCreatePublicFunctionSymbol;
        return (signature == null || (irSimpleFunctionSymbolCreatePublicFunctionSymbol = createPublicFunctionSymbol(declaration, signature)) == null) ? createPrivateFunctionSymbol(declaration) : irSimpleFunctionSymbolCreatePublicFunctionSymbol;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected IrSimpleFunctionSymbol createPublicFunctionSymbol(Function declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return new IrSimpleFunctionPublicSymbolImpl(signature, null, 2, 0 == true ? 1 : 0);
    }

    protected IrSimpleFunctionSymbol createPrivateFunctionSymbol(Function declaration) {
        return new IrSimpleFunctionSymbolImpl(null, 1, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrTypeParameter declareGlobalTypeParameter(final TypeParameter declaration, Function1<? super IrTypeParameterSymbol, ? extends IrTypeParameter> typeParameterFactory) {
        IrTypeParameter irTypeParameterDeclareGlobalTypeParameter;
        Intrinsics.checkNotNullParameter(typeParameterFactory, "typeParameterFactory");
        SymbolTableSlice.Flat<TypeParameter, IrTypeParameter, IrTypeParameterSymbol> flat = this.globalTypeParameterSlice;
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature != null) {
            irTypeParameterDeclareGlobalTypeParameter = getTable().declareGlobalTypeParameter(idSignatureCalculateSignature, new Function0<IrTypeParameterSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareGlobalTypeParameter$$inlined$declare$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrTypeParameterSymbol invoke() {
                    return this.createTypeParameterSymbol(declaration, idSignatureCalculateSignature);
                }
            }, typeParameterFactory);
        } else {
            Function0<IrTypeParameterSymbol> function0 = new Function0<IrTypeParameterSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$declareGlobalTypeParameter$$inlined$declare$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrTypeParameterSymbol invoke() {
                    return this.createTypeParameterSymbol(declaration, idSignatureCalculateSignature);
                }
            };
            synchronized (flat.getLock()) {
                IrTypeParameterSymbol irTypeParameterSymbol = flat.get(declaration);
                if (irTypeParameterSymbol == null) {
                    IrTypeParameterSymbol irTypeParameterSymbolInvoke = function0.invoke();
                    flat.set(declaration, irTypeParameterSymbolInvoke);
                    irTypeParameterSymbol = irTypeParameterSymbolInvoke;
                } else {
                    flat.getUnboundSymbols().remove(irTypeParameterSymbol);
                }
                irTypeParameterDeclareGlobalTypeParameter = typeParameterFactory.invoke(irTypeParameterSymbol);
                if (!irTypeParameterSymbol.isBound()) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (irTypeParameterSymbol.getOwner() != irTypeParameterDeclareGlobalTypeParameter) {
                    throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(irTypeParameterSymbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(irTypeParameterDeclareGlobalTypeParameter, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
            }
        }
        return irTypeParameterDeclareGlobalTypeParameter;
    }

    public final IrTypeParameter declareGlobalTypeParameter(final int startOffset, final int endOffset, final IrDeclarationOrigin origin, final TypeParameter declaration) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        return declareGlobalTypeParameter(declaration, new Function1<IrTypeParameterSymbol, IrTypeParameter>(this) { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension.declareGlobalTypeParameter.3
            final /* synthetic */ SymbolTableExtension<Declaration, Class, TypeAlias, Script, Function, Constructor, Property, ValueParameter, TypeParameter> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrTypeParameter invoke(IrTypeParameterSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.defaultTypeParameterFactory(startOffset, endOffset, origin, declaration, it);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IrTypeParameter declareScopedTypeParameter$default(SymbolTableExtension symbolTableExtension, final int i, final int i2, final IrDeclarationOrigin irDeclarationOrigin, final Object obj, Function1 function1, int i3, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: declareScopedTypeParameter");
        }
        if ((i3 & 16) != 0) {
            function1 = new Function1<IrTypeParameterSymbol, IrTypeParameter>(symbolTableExtension) { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension.declareScopedTypeParameter.1
                final /* synthetic */ SymbolTableExtension<Declaration, Class, TypeAlias, Script, Function, Constructor, Property, ValueParameter, TypeParameter> this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                    this.this$0 = symbolTableExtension;
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrTypeParameter invoke(IrTypeParameterSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.this$0.defaultTypeParameterFactory(i, i2, irDeclarationOrigin, obj, it);
                }
            };
        }
        return symbolTableExtension.declareScopedTypeParameter(i, i2, irDeclarationOrigin, obj, function1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrTypeParameter declareScopedTypeParameter(int startOffset, int endOffset, IrDeclarationOrigin origin, TypeParameter declaration, Function1<? super IrTypeParameterSymbol, ? extends IrTypeParameter> typeParameterFactory) {
        IrTypeParameter irTypeParameterInvoke;
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(typeParameterFactory, "typeParameterFactory");
        SymbolTableSlice.Scoped<TypeParameter, IrTypeParameter, IrTypeParameterSymbol> scopedTypeParameterSlice = getScopedTypeParameterSlice();
        synchronized (scopedTypeParameterSlice.getLock()) {
            IrTypeParameterSymbol irTypeParameterSymbol = scopedTypeParameterSlice.get(declaration);
            if (irTypeParameterSymbol == null) {
                IrTypeParameterSymbol irTypeParameterSymbolCreateTypeParameterSymbol = createTypeParameterSymbol(declaration, calculateSignature(declaration));
                scopedTypeParameterSlice.set(declaration, irTypeParameterSymbolCreateTypeParameterSymbol);
                irTypeParameterSymbol = irTypeParameterSymbolCreateTypeParameterSymbol;
            } else {
                scopedTypeParameterSlice.getUnboundSymbols().remove(irTypeParameterSymbol);
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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
    public IrTypeParameterSymbol referenceTypeParameter(final TypeParameter declaration) {
        IrTypeParameterSymbol irTypeParameterSymbolInvoke;
        IrTypeParameterSymbol irTypeParameterSymbolCreateTypeParameterSymbol;
        Object obj;
        IrTypeParameterSymbol irTypeParameterSymbol = (IrTypeParameterSymbol) getScopedTypeParameterSlice().get(declaration);
        if (irTypeParameterSymbol != null) {
            return irTypeParameterSymbol;
        }
        SymbolTableSlice.Flat<TypeParameter, IrTypeParameter, IrTypeParameterSymbol> flat = this.globalTypeParameterSlice;
        final IdSignature idSignatureCalculateSignature = calculateSignature(declaration);
        if (idSignatureCalculateSignature == null) {
            synchronized (flat.getLock()) {
                irTypeParameterSymbolCreateTypeParameterSymbol = flat.get(declaration);
                if (irTypeParameterSymbolCreateTypeParameterSymbol == null) {
                    irTypeParameterSymbolCreateTypeParameterSymbol = createTypeParameterSymbol(declaration, idSignatureCalculateSignature);
                    flat.getUnboundSymbols().add(irTypeParameterSymbolCreateTypeParameterSymbol);
                    flat.set(declaration, irTypeParameterSymbolCreateTypeParameterSymbol);
                }
            }
        } else {
            SymbolTable table = getTable();
            Function0<IrTypeParameterSymbol> function0 = new Function0<IrTypeParameterSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$referenceTypeParameter$$inlined$reference$default$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrTypeParameterSymbol invoke() {
                    return this.createPublicTypeParameterSymbol(declaration, idSignatureCalculateSignature);
                }
            };
            Function0<IrTypeParameterSymbol> function1 = new Function0<IrTypeParameterSymbol>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension$referenceTypeParameter$$inlined$reference$default$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrTypeParameterSymbol invoke() {
                    return this.createPrivateTypeParameterSymbol(declaration);
                }
            };
            Function0<IrTypeParameterSymbol> function2 = function0;
            if (idSignatureCalculateSignature.isPubliclyVisible()) {
                SymbolTableSlice.Flat flat2 = table.globalTypeParameterSlice;
                synchronized (flat2.getLock()) {
                    obj = flat2.get(idSignatureCalculateSignature);
                    if (obj == null) {
                        obj = (IrBindableSymbol) ((IrTypeParameterSymbol) function2.invoke());
                        flat2.getUnboundSymbols().add(obj);
                        flat2.set(idSignatureCalculateSignature, obj);
                    }
                }
                irTypeParameterSymbolInvoke = (IrTypeParameterSymbol) obj;
            } else {
                irTypeParameterSymbolInvoke = function1.invoke();
                irTypeParameterSymbolInvoke.setPrivateSignature(idSignatureCalculateSignature);
            }
            irTypeParameterSymbolCreateTypeParameterSymbol = irTypeParameterSymbolInvoke;
        }
        return irTypeParameterSymbolCreateTypeParameterSymbol;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
    public IrTypeParameterSymbol referenceScopedTypeParameter(TypeParameter declaration) {
        IrTypeParameterSymbol irTypeParameterSymbolCreateTypeParameterSymbol;
        SymbolTableSlice.Scoped<TypeParameter, IrTypeParameter, IrTypeParameterSymbol> scopedTypeParameterSlice = getScopedTypeParameterSlice();
        synchronized (scopedTypeParameterSlice.getLock()) {
            irTypeParameterSymbolCreateTypeParameterSymbol = scopedTypeParameterSlice.get(declaration);
            if (irTypeParameterSymbolCreateTypeParameterSymbol == null) {
                irTypeParameterSymbolCreateTypeParameterSymbol = createTypeParameterSymbol(declaration, calculateSignature(declaration));
                scopedTypeParameterSlice.getUnboundSymbols().add(irTypeParameterSymbolCreateTypeParameterSymbol);
                scopedTypeParameterSlice.set(declaration, irTypeParameterSymbolCreateTypeParameterSymbol);
            }
        }
        return irTypeParameterSymbolCreateTypeParameterSymbol;
    }

    protected final IrTypeParameterSymbol createTypeParameterSymbol(TypeParameter declaration, IdSignature signature) {
        IrTypeParameterSymbol irTypeParameterSymbolCreatePublicTypeParameterSymbol;
        return (signature == null || (irTypeParameterSymbolCreatePublicTypeParameterSymbol = createPublicTypeParameterSymbol(declaration, signature)) == null) ? createPrivateTypeParameterSymbol(declaration) : irTypeParameterSymbolCreatePublicTypeParameterSymbol;
    }

    protected IrTypeParameterSymbol createPublicTypeParameterSymbol(TypeParameter declaration, IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return new IrTypeParameterPublicSymbolImpl(signature, null, 2, null);
    }

    protected IrTypeParameterSymbol createPrivateTypeParameterSymbol(TypeParameter declaration) {
        return new IrTypeParameterSymbolImpl(null, 1, null);
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
    public IrValueParameterSymbol referenceValueParameter(ValueParameter declaration) {
        throw new IllegalStateException("There is no default implementation for any symbol table extension".toString());
    }

    public final void enterScope(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Iterator<T> it = getScopedSlices().iterator();
        while (it.hasNext()) {
            ((SymbolTableSlice.Scoped) it.next()).enterScope(symbol);
        }
    }

    public final void enterScope(IrDeclaration owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        enterScope(owner.getSymbol());
    }

    public final void leaveScope(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Iterator<T> it = getScopedSlices().iterator();
        while (it.hasNext()) {
            ((SymbolTableSlice.Scoped) it.next()).leaveScope(symbol);
        }
    }

    public final void leaveScope(IrDeclaration owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        leaveScope(owner.getSymbol());
    }

    @DelicateSymbolTableApi
    public final void forEachDeclarationSymbol(final Function1<? super IrSymbol, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        this.table.forEachDeclarationSymbol(block);
        this.scriptSlice.forEachSymbol$ir_tree(new Function1<IrSymbol, Unit>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension.forEachDeclarationSymbol.1
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
        this.classSlice.forEachSymbol$ir_tree(new Function1<IrSymbol, Unit>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension.forEachDeclarationSymbol.2
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
        this.constructorSlice.forEachSymbol$ir_tree(new Function1<IrSymbol, Unit>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension.forEachDeclarationSymbol.3
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
        this.enumEntrySlice.forEachSymbol$ir_tree(new Function1<IrSymbol, Unit>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension.forEachDeclarationSymbol.4
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
        this.fieldSlice.forEachSymbol$ir_tree(new Function1<IrSymbol, Unit>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension.forEachDeclarationSymbol.5
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
        this.functionSlice.forEachSymbol$ir_tree(new Function1<IrSymbol, Unit>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension.forEachDeclarationSymbol.6
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
        this.propertySlice.forEachSymbol$ir_tree(new Function1<IrSymbol, Unit>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension.forEachDeclarationSymbol.7
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
        this.typeAliasSlice.forEachSymbol$ir_tree(new Function1<IrSymbol, Unit>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension.forEachDeclarationSymbol.8
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
        this.globalTypeParameterSlice.forEachSymbol$ir_tree(new Function1<IrSymbol, Unit>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableExtension.forEachDeclarationSymbol.9
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
        setCreateSetBuilder.addAll(this.table.getAllUnboundSymbols());
        _get_allUnboundSymbols_$lambda$86$addUnbound(setCreateSetBuilder, this.scriptSlice);
        _get_allUnboundSymbols_$lambda$86$addUnbound(setCreateSetBuilder, this.classSlice);
        _get_allUnboundSymbols_$lambda$86$addUnbound(setCreateSetBuilder, this.constructorSlice);
        _get_allUnboundSymbols_$lambda$86$addUnbound(setCreateSetBuilder, this.enumEntrySlice);
        _get_allUnboundSymbols_$lambda$86$addUnbound(setCreateSetBuilder, this.fieldSlice);
        _get_allUnboundSymbols_$lambda$86$addUnbound(setCreateSetBuilder, this.functionSlice);
        _get_allUnboundSymbols_$lambda$86$addUnbound(setCreateSetBuilder, this.propertySlice);
        _get_allUnboundSymbols_$lambda$86$addUnbound(setCreateSetBuilder, this.typeAliasSlice);
        _get_allUnboundSymbols_$lambda$86$addUnbound(setCreateSetBuilder, this.globalTypeParameterSlice);
        return SetsKt.build(setCreateSetBuilder);
    }

    private static final void _get_allUnboundSymbols_$lambda$86$addUnbound(Set<IrSymbol> set, SymbolTableSlice<?, ?, ?> symbolTableSlice) {
        for (Object obj : symbolTableSlice.getUnboundSymbols()) {
            if (!((IrBindableSymbol) obj).isBound()) {
                set.add(obj);
            }
        }
    }
}
