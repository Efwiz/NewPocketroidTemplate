package org.jetbrains.kotlin.backend.common;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;

/* JADX INFO: compiled from: IrElementTransformerVoidWithContext.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020$H\u0014J\u0010\u0010%\u001a\u00020&2\u0006\u0010#\u001a\u00020$H\u0004J\b\u0010'\u001a\u00020&H\u0004J\u000e\u0010(\u001a\u00020)2\u0006\u0010#\u001a\u00020*J\u0010\u0010+\u001a\u00020)2\u0006\u0010#\u001a\u00020*H\u0016J\u000e\u0010,\u001a\u00020)2\u0006\u0010#\u001a\u00020-J\u0010\u0010.\u001a\u00020)2\u0006\u0010#\u001a\u00020-H\u0016J\u000e\u0010/\u001a\u00020)2\u0006\u0010#\u001a\u000200J\u0010\u00101\u001a\u00020)2\u0006\u0010#\u001a\u000200H\u0016J\u000e\u00102\u001a\u00020\u00122\u0006\u0010#\u001a\u00020\u0012J\u0010\u00103\u001a\u00020\u00122\u0006\u0010#\u001a\u00020\u0012H\u0016J\u000e\u00104\u001a\u00020)2\u0006\u0010#\u001a\u000205J\u0010\u00106\u001a\u00020)2\u0006\u0010#\u001a\u000205H\u0016J\u000e\u00107\u001a\u00020)2\u0006\u0010#\u001a\u000208J\u0010\u00109\u001a\u00020)2\u0006\u0010#\u001a\u000208H\u0016J\u000e\u0010:\u001a\u00020)2\u0006\u0010#\u001a\u00020;J\u0010\u0010<\u001a\u00020)2\u0006\u0010#\u001a\u00020;H\u0016J\u000e\u0010=\u001a\u00020)2\u0006\u0010#\u001a\u00020>J\u0010\u0010?\u001a\u00020)2\u0006\u0010#\u001a\u00020>H\u0016J-\u0010@\u001a\u0002HA\"\u0004\b\u0000\u0010A2\u0006\u0010#\u001a\u00020$2\f\u0010B\u001a\b\u0012\u0004\u0012\u0002HA0CH\u0084\bø\u0001\u0000¢\u0006\u0002\u0010DR\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\b\u001a\u0004\u0018\u00010\u00058DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\u00058DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0016\u0010\u0015\u001a\u0004\u0018\u00010\u00058DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\nR\u0016\u0010\u0017\u001a\u0004\u0018\u00010\u00058DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\nR\u0016\u0010\u0019\u001a\u0004\u0018\u00010\u00058DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\nR\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u00058DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\nR\u0016\u0010\u001d\u001a\u0004\u0018\u00010\u00058DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\nR\u0016\u0010\u001f\u001a\u0004\u0018\u00010\u00058DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b \u0010\nR\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006E"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "()V", "allScopes", "", "Lorg/jetbrains/kotlin/backend/common/ScopeWithIr;", "getAllScopes", "()Ljava/util/List;", "currentAnonymousInitializer", "getCurrentAnonymousInitializer", "()Lorg/jetbrains/kotlin/backend/common/ScopeWithIr;", "currentClass", "getCurrentClass", "currentDeclarationParent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getCurrentDeclarationParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "currentFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "getCurrentFile", "()Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "currentFunction", "getCurrentFunction", "currentProperty", "getCurrentProperty", "currentScope", "getCurrentScope", "currentScript", "getCurrentScript", "currentValueParameter", "getCurrentValueParameter", "parentScope", "getParentScope", "scopeStack", "createScope", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "unsafeEnterScope", "", "unsafeLeaveScope", "visitAnonymousInitializer", "Lorg/jetbrains/kotlin/ir/IrStatement;", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "visitAnonymousInitializerNew", "visitClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitClassNew", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitFieldNew", "visitFile", "visitFileNew", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitFunctionNew", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "visitPropertyNew", "visitScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "visitScriptNew", "visitValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "visitValueParameterNew", "withinScope", ExifInterface.GPS_DIRECTION_TRUE, "fn", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrElementTransformerVoidWithContext extends IrElementTransformerVoid {
    private final List<ScopeWithIr> scopeStack = new ArrayList();

    protected ScopeWithIr createScope(IrSymbolOwner declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return new ScopeWithIr(new Scope(declaration.getSymbol()), declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void unsafeEnterScope(IrSymbolOwner declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        UtilsKt.push(this.scopeStack, createScope(declaration));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void unsafeLeaveScope() {
        UtilsKt.pop(this.scopeStack);
    }

    protected final <T> T withinScope(IrSymbolOwner declaration, Function0<? extends T> fn) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(fn, "fn");
        unsafeEnterScope(declaration);
        T tInvoke = fn.invoke();
        unsafeLeaveScope();
        return tInvoke;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrFile visitFile(IrFile declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        UtilsKt.push(this.scopeStack, createScope(declaration));
        IrFile irFileVisitFileNew = visitFileNew(declaration);
        UtilsKt.pop(this.scopeStack);
        return irFileVisitFileNew;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrStatement visitClass(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        UtilsKt.push(this.scopeStack, createScope(declaration));
        IrStatement irStatementVisitClassNew = visitClassNew(declaration);
        UtilsKt.pop(this.scopeStack);
        return irStatementVisitClassNew;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrStatement visitProperty(IrProperty declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        UtilsKt.push(this.scopeStack, createScope(declaration));
        IrStatement irStatementVisitPropertyNew = visitPropertyNew(declaration);
        UtilsKt.pop(this.scopeStack);
        return irStatementVisitPropertyNew;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrStatement visitField(IrField declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        UtilsKt.push(this.scopeStack, createScope(declaration));
        IrStatement irStatementVisitFieldNew = visitFieldNew(declaration);
        UtilsKt.pop(this.scopeStack);
        return irStatementVisitFieldNew;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrStatement visitFunction(IrFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        UtilsKt.push(this.scopeStack, createScope(declaration));
        IrStatement irStatementVisitFunctionNew = visitFunctionNew(declaration);
        UtilsKt.pop(this.scopeStack);
        return irStatementVisitFunctionNew;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrStatement visitAnonymousInitializer(IrAnonymousInitializer declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        UtilsKt.push(this.scopeStack, createScope(declaration));
        IrStatement irStatementVisitAnonymousInitializerNew = visitAnonymousInitializerNew(declaration);
        UtilsKt.pop(this.scopeStack);
        return irStatementVisitAnonymousInitializerNew;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrStatement visitValueParameter(IrValueParameter declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        UtilsKt.push(this.scopeStack, createScope(declaration));
        IrStatement irStatementVisitValueParameterNew = visitValueParameterNew(declaration);
        UtilsKt.pop(this.scopeStack);
        return irStatementVisitValueParameterNew;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public final IrStatement visitScript(IrScript declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        UtilsKt.push(this.scopeStack, createScope(declaration));
        IrStatement irStatementVisitScriptNew = visitScriptNew(declaration);
        UtilsKt.pop(this.scopeStack);
        return irStatementVisitScriptNew;
    }

    protected final IrFile getCurrentFile() {
        ScopeWithIr scopeWithIrPrevious;
        List<ScopeWithIr> list = this.scopeStack;
        ListIterator<ScopeWithIr> listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            scopeWithIrPrevious = listIterator.previous();
            if (scopeWithIrPrevious.getIrElement() instanceof IrFile) {
                Intrinsics.checkNotNull(scopeWithIrPrevious);
                IrElement irElement = scopeWithIrPrevious.getIrElement();
                Intrinsics.checkNotNull(irElement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFile");
                return (IrFile) irElement;
            }
        }
        scopeWithIrPrevious = null;
        Intrinsics.checkNotNull(scopeWithIrPrevious);
        IrElement irElement2 = scopeWithIrPrevious.getIrElement();
        Intrinsics.checkNotNull(irElement2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFile");
        return (IrFile) irElement2;
    }

    protected final ScopeWithIr getCurrentScript() {
        ScopeWithIr scopeWithIrPrevious;
        List<ScopeWithIr> list = this.scopeStack;
        ListIterator<ScopeWithIr> listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            scopeWithIrPrevious = listIterator.previous();
            if (scopeWithIrPrevious.getScope().getScopeOwnerSymbol() instanceof IrScriptSymbol) {
                return scopeWithIrPrevious;
            }
        }
        scopeWithIrPrevious = null;
        return scopeWithIrPrevious;
    }

    protected final ScopeWithIr getCurrentClass() {
        ScopeWithIr scopeWithIrPrevious;
        List<ScopeWithIr> list = this.scopeStack;
        ListIterator<ScopeWithIr> listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            scopeWithIrPrevious = listIterator.previous();
            if (scopeWithIrPrevious.getScope().getScopeOwnerSymbol() instanceof IrClassSymbol) {
                return scopeWithIrPrevious;
            }
        }
        scopeWithIrPrevious = null;
        return scopeWithIrPrevious;
    }

    protected final ScopeWithIr getCurrentFunction() {
        ScopeWithIr scopeWithIrPrevious;
        List<ScopeWithIr> list = this.scopeStack;
        ListIterator<ScopeWithIr> listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            scopeWithIrPrevious = listIterator.previous();
            if (scopeWithIrPrevious.getScope().getScopeOwnerSymbol() instanceof IrFunctionSymbol) {
                return scopeWithIrPrevious;
            }
        }
        scopeWithIrPrevious = null;
        return scopeWithIrPrevious;
    }

    protected final ScopeWithIr getCurrentProperty() {
        ScopeWithIr scopeWithIrPrevious;
        List<ScopeWithIr> list = this.scopeStack;
        ListIterator<ScopeWithIr> listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            scopeWithIrPrevious = listIterator.previous();
            if (scopeWithIrPrevious.getScope().getScopeOwnerSymbol() instanceof IrPropertySymbol) {
                return scopeWithIrPrevious;
            }
        }
        scopeWithIrPrevious = null;
        return scopeWithIrPrevious;
    }

    protected final ScopeWithIr getCurrentAnonymousInitializer() {
        ScopeWithIr scopeWithIrPrevious;
        List<ScopeWithIr> list = this.scopeStack;
        ListIterator<ScopeWithIr> listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            scopeWithIrPrevious = listIterator.previous();
            if (scopeWithIrPrevious.getScope().getScopeOwnerSymbol() instanceof IrAnonymousInitializer) {
                return scopeWithIrPrevious;
            }
        }
        scopeWithIrPrevious = null;
        return scopeWithIrPrevious;
    }

    protected final ScopeWithIr getCurrentValueParameter() {
        ScopeWithIr scopeWithIrPrevious;
        List<ScopeWithIr> list = this.scopeStack;
        ListIterator<ScopeWithIr> listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            scopeWithIrPrevious = listIterator.previous();
            if (scopeWithIrPrevious.getScope().getScopeOwnerSymbol() instanceof IrValueParameter) {
                return scopeWithIrPrevious;
            }
        }
        scopeWithIrPrevious = null;
        return scopeWithIrPrevious;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final ScopeWithIr getCurrentScope() {
        return (ScopeWithIr) UtilsKt.peek(this.scopeStack);
    }

    protected final ScopeWithIr getParentScope() {
        if (this.scopeStack.size() < 2) {
            return null;
        }
        List<ScopeWithIr> list = this.scopeStack;
        return list.get(list.size() - 2);
    }

    protected final List<ScopeWithIr> getAllScopes() {
        return this.scopeStack;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final IrDeclarationParent getCurrentDeclarationParent() {
        ScopeWithIr scopeWithIrPrevious;
        List<ScopeWithIr> list = this.scopeStack;
        ListIterator<ScopeWithIr> listIterator = list.listIterator(list.size());
        do {
            if (!listIterator.hasPrevious()) {
                scopeWithIrPrevious = null;
                break;
            }
            scopeWithIrPrevious = listIterator.previous();
        } while (!(scopeWithIrPrevious.getIrElement() instanceof IrDeclarationParent));
        ScopeWithIr scopeWithIr = scopeWithIrPrevious;
        IrElement irElement = scopeWithIr != null ? scopeWithIr.getIrElement() : null;
        if (irElement instanceof IrDeclarationParent) {
            return (IrDeclarationParent) irElement;
        }
        return null;
    }

    public IrFile visitFileNew(IrFile declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitFile(declaration);
    }

    public IrStatement visitClassNew(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitClass(declaration);
    }

    public IrStatement visitFunctionNew(IrFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitFunction(declaration);
    }

    public IrStatement visitPropertyNew(IrProperty declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitProperty(declaration);
    }

    public IrStatement visitFieldNew(IrField declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitField(declaration);
    }

    public IrStatement visitAnonymousInitializerNew(IrAnonymousInitializer declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitAnonymousInitializer(declaration);
    }

    public IrStatement visitValueParameterNew(IrValueParameter declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitValueParameter(declaration);
    }

    public IrStatement visitScriptNew(IrScript declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return super.visitScript(declaration);
    }
}
