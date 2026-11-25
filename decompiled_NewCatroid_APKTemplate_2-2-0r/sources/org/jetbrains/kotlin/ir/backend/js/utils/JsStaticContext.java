package org.jetbrains.kotlin.ir.backend.js.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsGenerationGranularity;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformers;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrClassModel;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.TranslationMode;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: JsStaticContext.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0018\u0010\"\u001a\u0004\u0018\u00010#2\u0006\u0010$\u001a\u00020%H\u0096\u0001¢\u0006\u0002\u0010&J\u0011\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020%H\u0096\u0001J\u0011\u0010*\u001a\u00020(2\u0006\u0010+\u001a\u00020,H\u0096\u0001J\u0011\u0010-\u001a\u00020(2\u0006\u0010.\u001a\u00020/H\u0096\u0001J\u0011\u00100\u001a\u00020(2\u0006\u0010.\u001a\u00020/H\u0096\u0001J\u0011\u00101\u001a\u00020(2\u0006\u00102\u001a\u000203H\u0096\u0001J\u0011\u00104\u001a\u00020(2\u0006\u00105\u001a\u000206H\u0096\u0001J\u0011\u00107\u001a\u00020(2\u0006\u00108\u001a\u000209H\u0096\u0001J\u0011\u0010:\u001a\u00020(2\u0006\u00102\u001a\u000203H\u0096\u0001J\u0011\u0010;\u001a\u00020<2\u0006\u0010)\u001a\u00020%H\u0096\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u001a¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001d\u001a\u00020\u001e8F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b \u0010!¨\u0006="}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/JsStaticContext;", "Lorg/jetbrains/kotlin/ir/backend/js/utils/IrNamer;", "backendContext", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "irNamer", "globalNameScope", "Lorg/jetbrains/kotlin/ir/backend/js/utils/NameTable;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "mode", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/TranslationMode;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;Lorg/jetbrains/kotlin/ir/backend/js/utils/IrNamer;Lorg/jetbrains/kotlin/ir/backend/js/utils/NameTable;Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/TranslationMode;)V", "getBackendContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "classModels", "", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrClassModel;", "getClassModels", "()Ljava/util/Map;", "getGlobalNameScope", "()Lorg/jetbrains/kotlin/ir/backend/js/utils/NameTable;", "initializerBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "getInitializerBlock", "()Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "intrinsics", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIntrinsicTransformers;", "getIntrinsics", "()Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIntrinsicTransformers;", "isPerFile", "", "()Z", "getMode", "()Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/TranslationMode;", "getAssociatedObjectKey", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Ljava/lang/Integer;", "getNameForClass", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "klass", "getNameForConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getNameForField", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getNameForMemberField", "getNameForMemberFunction", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getNameForProperty", "property", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "getNameForStaticDeclaration", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "getNameForStaticFunction", "getRefForExternalClass", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsStaticContext implements IrNamer {
    private final JsIrBackendContext backendContext;
    private final Map<IrClassSymbol, JsIrClassModel> classModels;
    private final NameTable<IrDeclaration> globalNameScope;
    private final JsCompositeBlock initializerBlock;
    private final JsIntrinsicTransformers intrinsics;
    private final IrNamer irNamer;
    private final TranslationMode mode;

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public Integer getAssociatedObjectKey(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        return this.irNamer.getAssociatedObjectKey(irClass);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForClass(IrClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return this.irNamer.getNameForClass(klass);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForConstructor(IrConstructor constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return this.irNamer.getNameForConstructor(constructor);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForField(IrField field) {
        Intrinsics.checkNotNullParameter(field, "field");
        return this.irNamer.getNameForField(field);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForMemberField(IrField field) {
        Intrinsics.checkNotNullParameter(field, "field");
        return this.irNamer.getNameForMemberField(field);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForMemberFunction(IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return this.irNamer.getNameForMemberFunction(function);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForProperty(IrProperty property) {
        Intrinsics.checkNotNullParameter(property, "property");
        return this.irNamer.getNameForProperty(property);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForStaticDeclaration(IrDeclarationWithName declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return this.irNamer.getNameForStaticDeclaration(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForStaticFunction(IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return this.irNamer.getNameForStaticFunction(function);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsNameRef getRefForExternalClass(IrClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return this.irNamer.getRefForExternalClass(klass);
    }

    public JsStaticContext(JsIrBackendContext backendContext, IrNamer irNamer, NameTable<IrDeclaration> globalNameScope, TranslationMode mode) {
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        Intrinsics.checkNotNullParameter(irNamer, "irNamer");
        Intrinsics.checkNotNullParameter(globalNameScope, "globalNameScope");
        Intrinsics.checkNotNullParameter(mode, "mode");
        this.backendContext = backendContext;
        this.irNamer = irNamer;
        this.globalNameScope = globalNameScope;
        this.mode = mode;
        this.intrinsics = new JsIntrinsicTransformers(backendContext);
        this.classModels = new LinkedHashMap();
        this.initializerBlock = new JsCompositeBlock();
    }

    public final JsIrBackendContext getBackendContext() {
        return this.backendContext;
    }

    public final NameTable<IrDeclaration> getGlobalNameScope() {
        return this.globalNameScope;
    }

    public final TranslationMode getMode() {
        return this.mode;
    }

    public final JsIntrinsicTransformers getIntrinsics() {
        return this.intrinsics;
    }

    public final Map<IrClassSymbol, JsIrClassModel> getClassModels() {
        return this.classModels;
    }

    public final JsCompositeBlock getInitializerBlock() {
        return this.initializerBlock;
    }

    public final boolean isPerFile() {
        return this.mode.getGranularity() == JsGenerationGranularity.PER_FILE;
    }
}
