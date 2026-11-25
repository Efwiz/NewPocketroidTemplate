package org.jetbrains.kotlin.ir.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.symbols.IrBindableSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.util.PrivateForInline;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: SymbolTableSlice.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0004\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0002*\u00020\u0003*\u0012\b\u0002\u0010\u0004*\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002H\u00020\u00052\u00020\u0006:\u0002$%B\r\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ-\u0010\u0011\u001a\u00028\u00012\u0006\u0010\u0012\u001a\u00028\u00022\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00010\u0014H\u0081\bø\u0001\u0000¢\u0006\u0002\u0010\u0015J;\u0010\u0016\u001a\u00028\u00012\u0006\u0010\u0017\u001a\u00028\u00002\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00020\u00192\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00010\u0014H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001aJ;\u0010\u001b\u001a\u00028\u00012\u0006\u0010\u0017\u001a\u00028\u00002\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00020\u00192\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00010\u0014H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001aJ\u0017\u0010\u001c\u001a\u0004\u0018\u00018\u00022\u0006\u0010\u0017\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u001dJ'\u0010\u001e\u001a\u00028\u00022\u0006\u0010\u0017\u001a\u00028\u00002\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00020\u0019H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010 J\u001d\u0010!\u001a\u00020\"2\u0006\u0010\u0017\u001a\u00028\u00002\u0006\u0010\u0012\u001a\u00028\u0002H&¢\u0006\u0002\u0010#R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR!\u0010\f\u001a\u0012\u0012\u0004\u0012\u00028\u00020\rj\b\u0012\u0004\u0012\u00028\u0002`\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice;", "Key", "SymbolOwner", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", NativeSymbol.CLASS_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "(Lorg/jetbrains/kotlin/ir/IrLock;)V", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "unboundSymbols", "Ljava/util/LinkedHashSet;", "Lkotlin/collections/LinkedHashSet;", "getUnboundSymbols", "()Ljava/util/LinkedHashSet;", "createOwnerSafe", NativeSymbol.TYPE_NAME, "createOwner", "Lkotlin/Function1;", "(Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "declare", ASTExpr.DEFAULT_MAP_KEY_NAME, "createSymbol", "Lkotlin/Function0;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "declareIfNotExists", "get", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "referenced", "orElse", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;)V", "Flat", "Scoped", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SymbolTableSlice<Key, SymbolOwner extends IrSymbolOwner, Symbol extends IrBindableSymbol<?, SymbolOwner>> {
    private final IrLock lock;
    private final LinkedHashSet<Symbol> unboundSymbols;

    public abstract Symbol get(Key key);

    public abstract void set(Key key, Symbol symbol);

    public SymbolTableSlice(IrLock lock) {
        Intrinsics.checkNotNullParameter(lock, "lock");
        this.lock = lock;
        this.unboundSymbols = new LinkedHashSet<>();
    }

    public final IrLock getLock() {
        return this.lock;
    }

    public final LinkedHashSet<Symbol> getUnboundSymbols() {
        return this.unboundSymbols;
    }

    public final SymbolOwner declare(Key key, Function0<? extends Symbol> createSymbol, Function1<? super Symbol, ? extends SymbolOwner> createOwner) {
        SymbolOwner symbolownerInvoke;
        Intrinsics.checkNotNullParameter(createSymbol, "createSymbol");
        Intrinsics.checkNotNullParameter(createOwner, "createOwner");
        synchronized (getLock()) {
            try {
                Symbol symbol = get(key);
                if (symbol == null) {
                    Symbol symbolInvoke = createSymbol.invoke();
                    set(key, symbolInvoke);
                    symbol = symbolInvoke;
                } else {
                    getUnboundSymbols().remove(symbol);
                }
                symbolownerInvoke = createOwner.invoke(symbol);
                if (!symbol.isBound()) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (symbol.getOwner() == symbolownerInvoke) {
                    InlineMarker.finallyStart(2);
                } else {
                    throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(symbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(symbolownerInvoke, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
            } catch (Throwable th) {
                InlineMarker.finallyStart(1);
                InlineMarker.finallyEnd(1);
                throw th;
            }
        }
        InlineMarker.finallyEnd(2);
        return symbolownerInvoke;
    }

    public final Symbol referenced(Key key, Function0<? extends Symbol> orElse) {
        Symbol symbolInvoke;
        Intrinsics.checkNotNullParameter(orElse, "orElse");
        synchronized (getLock()) {
            try {
                symbolInvoke = (Symbol) get(key);
                if (symbolInvoke == null) {
                    SymbolTableSlice<Key, SymbolOwner, Symbol> symbolTableSlice = this;
                    symbolInvoke = orElse.invoke();
                    getUnboundSymbols().add(symbolInvoke);
                    set(key, symbolInvoke);
                }
                InlineMarker.finallyStart(2);
            } finally {
                int i = 1;
                InlineMarker.finallyStart(i);
                InlineMarker.finallyEnd(i);
            }
        }
        return symbolInvoke;
    }

    public final SymbolOwner declareIfNotExists(Key key, Function0<? extends Symbol> createSymbol, Function1<? super Symbol, ? extends SymbolOwner> createOwner) {
        Intrinsics.checkNotNullParameter(createSymbol, "createSymbol");
        Intrinsics.checkNotNullParameter(createOwner, "createOwner");
        synchronized (getLock()) {
            try {
                Symbol symbolInvoke = get(key);
                if (symbolInvoke == null) {
                    symbolInvoke = createSymbol.invoke();
                    set(key, symbolInvoke);
                } else if (symbolInvoke.isBound()) {
                    getUnboundSymbols().remove(symbolInvoke);
                    SymbolOwner symbolowner = (SymbolOwner) symbolInvoke.getOwner();
                    InlineMarker.finallyStart(3);
                    InlineMarker.finallyEnd(3);
                    return symbolowner;
                }
                SymbolOwner symbolownerInvoke = createOwner.invoke(symbolInvoke);
                if (!symbolInvoke.isBound()) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (symbolInvoke.getOwner() == symbolownerInvoke) {
                    InlineMarker.finallyStart(2);
                    InlineMarker.finallyEnd(2);
                    return symbolownerInvoke;
                }
                throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(symbolInvoke.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(symbolownerInvoke, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            } catch (Throwable th) {
                InlineMarker.finallyStart(1);
                InlineMarker.finallyEnd(1);
                throw th;
            }
        }
    }

    @PrivateForInline
    public final SymbolOwner createOwnerSafe(Symbol symbol, Function1<? super Symbol, ? extends SymbolOwner> createOwner) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(createOwner, "createOwner");
        SymbolOwner symbolownerInvoke = createOwner.invoke(symbol);
        if (!symbol.isBound()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (symbol.getOwner() == symbolownerInvoke) {
            return symbolownerInvoke;
        }
        throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(symbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(symbolownerInvoke, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }

    /* JADX INFO: compiled from: SymbolTableSlice.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000*\u0004\b\u0003\u0010\u0001*\b\b\u0004\u0010\u0002*\u00020\u0003*\u0012\b\u0005\u0010\u0004*\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002H\u00020\u00052\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00040\u0006B#\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0014\b\u0002\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0005\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\u0010\fJ!\u0010\u0012\u001a\u00020\u00132\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00130\nH\u0001¢\u0006\u0002\b\u0016J\u0017\u0010\u0017\u001a\u0004\u0018\u00018\u00052\u0006\u0010\u0018\u001a\u00028\u0003H\u0016¢\u0006\u0002\u0010\u0019J\u001d\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00028\u00032\u0006\u0010\u001b\u001a\u00028\u0005H\u0016¢\u0006\u0002\u0010\u001cR*\u0010\r\u001a\u001e\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u00050\u000ej\u000e\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u0005`\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0005\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice$Flat;", "Key", "SymbolOwner", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", NativeSymbol.CLASS_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "symbolFilter", "Lkotlin/Function1;", "", "(Lorg/jetbrains/kotlin/ir/IrLock;Lkotlin/jvm/functions/Function1;)V", "signatureToSymbol", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "getSymbolFilter", "()Lkotlin/jvm/functions/Function1;", "forEachSymbol", "", "block", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "forEachSymbol$ir_tree", "get", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, NativeSymbol.TYPE_NAME, "(Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Flat<Key, SymbolOwner extends IrSymbolOwner, Symbol extends IrBindableSymbol<?, SymbolOwner>> extends SymbolTableSlice<Key, SymbolOwner, Symbol> {
        private final HashMap<Key, Symbol> signatureToSymbol;
        private final Function1<Symbol, Boolean> symbolFilter;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public Flat(IrLock lock, Function1<? super Symbol, Boolean> symbolFilter) {
            super(lock);
            Intrinsics.checkNotNullParameter(lock, "lock");
            Intrinsics.checkNotNullParameter(symbolFilter, "symbolFilter");
            this.symbolFilter = symbolFilter;
            this.signatureToSymbol = new HashMap<>();
        }

        public /* synthetic */ Flat(IrLock irLock, AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(irLock, (i & 2) != 0 ? new Function1<Symbol, Boolean>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableSlice.Flat.1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(Symbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return true;
                }
            } : anonymousClass1);
        }

        public final Function1<Symbol, Boolean> getSymbolFilter() {
            return this.symbolFilter;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolTableSlice
        public Symbol get(Key key) {
            return this.signatureToSymbol.get(key);
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolTableSlice
        public void set(Key key, Symbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            if (this.symbolFilter.invoke(symbol).booleanValue()) {
                this.signatureToSymbol.put(key, symbol);
            }
        }

        @SymbolTableInternals
        public final void forEachSymbol$ir_tree(Function1<? super IrSymbol, Unit> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            Iterator<Map.Entry<Key, Symbol>> it = this.signatureToSymbol.entrySet().iterator();
            while (it.hasNext()) {
                block.invoke(it.next().getValue());
            }
        }
    }

    /* JADX INFO: compiled from: SymbolTableSlice.kt */
    @Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0001\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0003\u0010\u0001*\b\b\u0004\u0010\u0002*\u00020\u0003*\u0012\b\u0005\u0010\u0004*\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002H\u00020\u00052\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00040\u0006:\u0001(B\r\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ;\u0010\u0011\u001a\u00028\u00042\u0006\u0010\u0012\u001a\u00028\u00032\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00050\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00028\u0005\u0012\u0004\u0012\u00028\u00040\u0016H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0017J\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dJ\u0017\u0010\u001e\u001a\u0004\u0018\u00018\u00052\u0006\u0010\u0012\u001a\u00028\u0003H\u0016¢\u0006\u0002\u0010\u001fJ\u001b\u0010 \u001a\u00020\u001b2\u0006\u0010!\u001a\u00028\u00032\u0006\u0010\"\u001a\u00028\u0005¢\u0006\u0002\u0010#J\u000e\u0010$\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dJ\b\u0010%\u001a\u00020&H\u0001J\u001d\u0010'\u001a\u00020\u001b2\u0006\u0010\u0012\u001a\u00028\u00032\u0006\u0010\"\u001a\u00028\u0005H\u0016¢\u0006\u0002\u0010#RB\u0010\f\u001a\u0010\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u0005\u0018\u00010\u000b2\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u0005\u0018\u00010\u000b8\u0000@BX\u0081\u000e¢\u0006\u000e\n\u0000\u0012\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice$Scoped;", "Key", "SymbolOwner", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", NativeSymbol.CLASS_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "(Lorg/jetbrains/kotlin/ir/IrLock;)V", "<set-?>", "Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice$Scoped$SliceScope;", "currentScope", "getCurrentScope$annotations", "()V", "getCurrentScope", "()Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice$Scoped$SliceScope;", "declareLocal", ASTExpr.DEFAULT_MAP_KEY_NAME, "createSymbol", "Lkotlin/Function0;", "createOwner", "Lkotlin/Function1;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", ArchiveStreamFactory.DUMP, "", "enterScope", "", "owner", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "get", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "introduceLocal", "descriptor", NativeSymbol.TYPE_NAME, "(Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;)V", "leaveScope", "noScope", "", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "SliceScope", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Scoped<Key, SymbolOwner extends IrSymbolOwner, Symbol extends IrBindableSymbol<?, SymbolOwner>> extends SymbolTableSlice<Key, SymbolOwner, Symbol> {
        private SliceScope<Key, Symbol> currentScope;

        @PrivateForInline
        public static /* synthetic */ void getCurrentScope$annotations() {
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Scoped(IrLock lock) {
            super(lock);
            Intrinsics.checkNotNullParameter(lock, "lock");
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolTableSlice
        public void set(Key key, Symbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            SliceScope<Key, Symbol> sliceScope = this.currentScope;
            if (sliceScope == null) {
                noScope();
                throw null;
            }
            sliceScope.set(key, symbol);
        }

        public final SliceScope<Key, Symbol> getCurrentScope() {
            return this.currentScope;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolTableSlice
        public Symbol get(Key key) {
            SliceScope<Key, Symbol> sliceScope = this.currentScope;
            if (sliceScope != null) {
                return sliceScope.get(key);
            }
            return null;
        }

        public final SymbolOwner declareLocal(Key key, Function0<? extends Symbol> createSymbol, Function1<? super Symbol, ? extends SymbolOwner> createOwner) {
            Intrinsics.checkNotNullParameter(createSymbol, "createSymbol");
            Intrinsics.checkNotNullParameter(createOwner, "createOwner");
            SliceScope<Key, Symbol> currentScope = getCurrentScope();
            if (currentScope == null) {
                noScope();
                throw null;
            }
            Symbol symbolInvoke = (Symbol) currentScope.getLocal(key);
            if (symbolInvoke == null) {
                symbolInvoke = createSymbol.invoke();
                currentScope.set(key, symbolInvoke);
            }
            SymbolOwner symbolownerInvoke = createOwner.invoke(symbolInvoke);
            if (!symbolInvoke.isBound()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (symbolInvoke.getOwner() == symbolownerInvoke) {
                return symbolownerInvoke;
            }
            throw new IllegalArgumentException(("Attempt to rebind an IR symbol or to re-create its owner: old owner " + RenderIrElementKt.render$default(symbolInvoke.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null) + ", new owner " + RenderIrElementKt.render$default(symbolownerInvoke, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }

        public final void introduceLocal(Key descriptor, Symbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            SliceScope<Key, Symbol> sliceScope = this.currentScope;
            if (sliceScope == null) {
                noScope();
                throw null;
            }
            Symbol symbol2 = sliceScope.get(descriptor);
            if (symbol2 == null) {
                sliceScope.set(descriptor, symbol);
                return;
            }
            throw new IllegalStateException((descriptor + " is already bound to " + symbol2).toString());
        }

        public final void enterScope(IrSymbol owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            this.currentScope = new SliceScope<>(owner, this.currentScope);
        }

        public final void leaveScope(IrSymbol owner) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            SliceScope<Key, Symbol> sliceScope = this.currentScope;
            IrSymbol owner2 = sliceScope != null ? sliceScope.getOwner() : null;
            if (!Intrinsics.areEqual(owner2, owner)) {
                throw new IllegalArgumentException(("Unexpected leaveScope: owner=" + owner + ", currentScope.owner=" + owner2).toString());
            }
            SliceScope<Key, Symbol> sliceScope2 = this.currentScope;
            SliceScope<Key, Symbol> parent = sliceScope2 != null ? sliceScope2.getParent() : null;
            this.currentScope = parent;
            if (parent != null && !getUnboundSymbols().isEmpty()) {
                throw new IllegalStateException(("Local scope contains unbound symbols: " + CollectionsKt.joinToString$default(getUnboundSymbols(), null, null, null, 0, null, new Function1<Symbol, CharSequence>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableSlice$Scoped$leaveScope$2
                    /* JADX WARN: Incorrect types in method signature: (TSymbol;)Ljava/lang/CharSequence; */
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(IrBindableSymbol it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return it.getDescriptor().toString();
                    }
                }, 31, null)).toString());
            }
        }

        public final String dump() {
            String strDump;
            SliceScope<Key, Symbol> sliceScope = this.currentScope;
            return (sliceScope == null || (strDump = sliceScope.dump()) == null) ? "<none>" : strDump;
        }

        @PrivateForInline
        public final Void noScope() {
            throw new IllegalStateException("No active scope".toString());
        }

        /* JADX INFO: compiled from: SymbolTableSlice.kt */
        @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0001\u0018\u0000*\u0004\b\u0006\u0010\u0001*\u0004\b\u0007\u0010\u00022\u00020\u0003B#\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0014\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00028\u0006\u0012\u0004\u0012\u00028\u0007\u0018\u00010\u0000¢\u0006\u0002\u0010\u0007J\u0006\u0010\u000f\u001a\u00020\u0010J\u0016\u0010\u0011\u001a\u00060\u0012j\u0002`\u00132\n\u0010\u0014\u001a\u00060\u0012j\u0002`\u0013J\u0018\u0010\u0015\u001a\u0004\u0018\u00018\u00072\u0006\u0010\u0016\u001a\u00028\u0006H\u0086\u0002¢\u0006\u0002\u0010\u0017J\u0015\u0010\u0018\u001a\u0004\u0018\u00018\u00072\u0006\u0010\u0016\u001a\u00028\u0006¢\u0006\u0002\u0010\u0017J\u001e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0016\u001a\u00028\u00062\u0006\u0010\u001b\u001a\u00028\u0007H\u0086\u0002¢\u0006\u0002\u0010\u001cR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001f\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00028\u0006\u0012\u0004\u0012\u00028\u0007\u0018\u00010\u0000¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR*\u0010\f\u001a\u001e\u0012\u0004\u0012\u00028\u0006\u0012\u0004\u0012\u00028\u00070\rj\u000e\u0012\u0004\u0012\u00028\u0006\u0012\u0004\u0012\u00028\u0007`\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice$Scoped$SliceScope;", "Key", NativeSymbol.CLASS_NAME, "", "owner", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "parent", "(Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice$Scoped$SliceScope;)V", "getOwner", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getParent", "()Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice$Scoped$SliceScope;", "signatureToSymbol", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", ArchiveStreamFactory.DUMP, "", "dumpTo", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "stringBuilder", "get", "descriptor", "(Ljava/lang/Object;)Ljava/lang/Object;", "getLocal", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "", NativeSymbol.TYPE_NAME, "(Ljava/lang/Object;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        @PrivateForInline
        public static final class SliceScope<Key, Symbol> {
            private final IrSymbol owner;
            private final SliceScope<Key, Symbol> parent;
            private final HashMap<Key, Symbol> signatureToSymbol;

            public SliceScope(IrSymbol owner, SliceScope<Key, Symbol> sliceScope) {
                Intrinsics.checkNotNullParameter(owner, "owner");
                this.owner = owner;
                this.parent = sliceScope;
                this.signatureToSymbol = new HashMap<>();
            }

            public final IrSymbol getOwner() {
                return this.owner;
            }

            public final SliceScope<Key, Symbol> getParent() {
                return this.parent;
            }

            public final Symbol get(Key descriptor) {
                Symbol symbol = this.signatureToSymbol.get(descriptor);
                if (symbol != null) {
                    return symbol;
                }
                SliceScope<Key, Symbol> sliceScope = this.parent;
                if (sliceScope != null) {
                    return sliceScope.get(descriptor);
                }
                return null;
            }

            public final Symbol getLocal(Key descriptor) {
                return this.signatureToSymbol.get(descriptor);
            }

            public final void set(Key descriptor, Symbol symbol) {
                this.signatureToSymbol.put(descriptor, symbol);
            }

            public final StringBuilder dumpTo(StringBuilder stringBuilder) {
                Intrinsics.checkNotNullParameter(stringBuilder, "stringBuilder");
                stringBuilder.append("owner=");
                stringBuilder.append(this.owner);
                stringBuilder.append("; ");
                Set<Key> setKeySet = this.signatureToSymbol.keySet();
                Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
                CollectionsKt.joinTo(setKeySet, stringBuilder, (112 & 2) != 0 ? ", " : null, (112 & 4) != 0 ? "" : "[", (112 & 8) != 0 ? "" : "]", (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : null);
                stringBuilder.append('\n');
                SliceScope<Key, Symbol> sliceScope = this.parent;
                if (sliceScope != null) {
                    sliceScope.dumpTo(stringBuilder);
                }
                return stringBuilder;
            }

            public final String dump() {
                String string = dumpTo(new StringBuilder()).toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        }
    }
}
