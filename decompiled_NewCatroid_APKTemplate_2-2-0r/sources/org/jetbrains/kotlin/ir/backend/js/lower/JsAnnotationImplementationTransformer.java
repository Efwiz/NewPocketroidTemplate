package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationTransformer;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: JsAnnotationImplementationLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\bH\u0016J \u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\rH\u0016J\u0018\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u00192\u0006\u0010\u001b\u001a\u00020\u001aH\u0016J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001b\u001a\u00020\u000fH\u0016J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsAnnotationImplementationTransformer;", "Lorg/jetbrains/kotlin/backend/common/lower/AnnotationImplementationTransformer;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "jsContext", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "arraysContentEquals", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getJsContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "chooseConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "implClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getArrayContentEqualsSymbol", ModuleXmlParser.TYPE, "implementAnnotationPropertiesAndConstructor", "", "annotationClass", "generatedConstructor", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "visitClassNew", "Lorg/jetbrains/kotlin/ir/IrStatement;", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsAnnotationImplementationTransformer extends AnnotationImplementationTransformer implements DeclarationTransformer {
    private final Map<IrType, IrSimpleFunctionSymbol> arraysContentEquals;
    private final JsIrBackendContext jsContext;

    public final JsIrBackendContext getJsContext() {
        return this.jsContext;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsAnnotationImplementationTransformer(JsIrBackendContext jsContext) {
        super(jsContext, null);
        Intrinsics.checkNotNullParameter(jsContext, "jsContext");
        this.jsContext = jsContext;
        Map<IrType, IrSimpleFunctionSymbol> arraysContentEquals = jsContext.getIr().getSymbols().getArraysContentEquals();
        if (arraysContentEquals == null) {
            throw new IllegalArgumentException("contentEquals symbols should be defined in JS IR context".toString());
        }
        this.arraysContentEquals = arraysContentEquals;
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrClass) {
            IrClass irClass = (IrClass) declaration;
            if (IrUtilsKt.isAnnotationClass(irClass)) {
                IrStatement irStatementVisitClassNew = visitClassNew(irClass);
                Intrinsics.checkNotNull(irStatementVisitClassNew, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                return CollectionsKt.listOf((IrClass) irStatementVisitClassNew);
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitConstructorCall(IrConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return expression;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationTransformer
    public IrConstructor chooseConstructor(IrClass implClass, IrConstructorCall expression) {
        Intrinsics.checkNotNullParameter(implClass, "implClass");
        Intrinsics.checkNotNullParameter(expression, "expression");
        CompilationExceptionKt.compilationException("Should not be called", (IrDeclaration) implClass);
        throw null;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationTransformer, org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
    public IrStatement visitClassNew(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (IrUtilsKt.isAnnotationClass(declaration)) {
            implementGeneratedFunctions(declaration, declaration);
        }
        addConstructorBodyForCompatibility(declaration);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationTransformer
    public IrSimpleFunctionSymbol getArrayContentEqualsSymbol(IrType type) {
        IrSimpleFunctionSymbol irSimpleFunctionSymbol;
        Intrinsics.checkNotNullParameter(type, "type");
        if (IrTypeUtilsKt.isPrimitiveArray(type) || IrTypeUtilsKt.isUnsignedArray(type)) {
            irSimpleFunctionSymbol = this.arraysContentEquals.get(type);
        } else {
            Iterator<T> it = this.arraysContentEquals.entrySet().iterator();
            boolean z = false;
            Object obj = null;
            while (true) {
                if (!it.hasNext()) {
                    if (!z) {
                        break;
                    }
                    break;
                }
                Object next = it.next();
                if (IrTypePredicatesKt.isArray((IrType) ((Map.Entry) next).getKey())) {
                    if (!z) {
                        z = true;
                        obj = next;
                    }
                }
                obj = null;
                break;
            }
            Map.Entry entry = (Map.Entry) obj;
            irSimpleFunctionSymbol = entry != null ? (IrSimpleFunctionSymbol) entry.getValue() : null;
        }
        if (irSimpleFunctionSymbol != null) {
            return irSimpleFunctionSymbol;
        }
        CompilationExceptionKt.compilationException("Can't find an Arrays.contentEquals method for array type", type);
        throw null;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationTransformer
    public void implementAnnotationPropertiesAndConstructor(IrClass implClass, IrClass annotationClass, IrConstructor generatedConstructor) {
        Intrinsics.checkNotNullParameter(implClass, "implClass");
        Intrinsics.checkNotNullParameter(annotationClass, "annotationClass");
        Intrinsics.checkNotNullParameter(generatedConstructor, "generatedConstructor");
        CompilationExceptionKt.compilationException("Should not be called", (IrDeclaration) implClass);
        throw null;
    }
}
