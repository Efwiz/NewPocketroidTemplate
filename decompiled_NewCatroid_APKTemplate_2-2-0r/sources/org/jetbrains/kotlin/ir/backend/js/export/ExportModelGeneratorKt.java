package org.jetbrains.kotlin.ir.backend.js.export;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.ir.DescriptorBasedUtilsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.lower.ES6ConstructorLoweringKt;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: ExportModelGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002\u001a\u0018\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\u0002\u001a\u0018\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\u0002\u001a\u0016\u0010\u0017\u001a\u00020\t*\u0006\u0012\u0002\b\u00030\u00182\u0006\u0010\u0014\u001a\u00020\u0015\u001a\u0012\u0010\u0019\u001a\u00020\t*\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015\u001a\u0012\u0010\u001a\u001a\u00020\t*\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015\u001a\u0016\u0010\u001b\u001a\u00020\t*\u0006\u0012\u0002\b\u00030\u00182\u0006\u0010\u0014\u001a\u00020\u0015\u001a\f\u0010\u001c\u001a\u00020\u001d*\u00020\u001eH\u0002\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0002X\u0082T¢\u0006\u0002\n\u0000\"\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0018\u0010\b\u001a\u00020\t*\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u000b\"\u0018\u0010\f\u001a\u00020\t*\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000e¨\u0006\u001f"}, d2 = {"allReservedWords", "", "", "magicPropertyName", "reservedWords", "strictModeReservedWords", "getStrictModeReservedWords", "()Ljava/util/Set;", "isInterface", "", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;)Z", "isStaticMethod", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Z", "getExportCandidate", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "shouldDeclarationBeExported", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "shouldDeclarationBeExportedImplicitlyOrExplicitly", "isAllowedFakeOverriddenDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "isExported", "isExportedImplicitlyOrExplicitly", "isOverriddenExported", "toExportedVisibility", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedVisibility;", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExportModelGeneratorKt {
    private static final Set<String> allReservedWords;
    private static final Set<String> reservedWords;
    private static final Set<String> strictModeReservedWords;

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isInterface(IrClassifierSymbol irClassifierSymbol) {
        IrSymbolOwner owner = irClassifierSymbol.getOwner();
        IrClass irClass = owner instanceof IrClass ? (IrClass) owner : null;
        return irClass != null && IrUtilsKt.isInterface(irClass);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isStaticMethod(IrFunction irFunction) {
        return ES6ConstructorLoweringKt.isEs6ConstructorReplacement(irFunction) || IrDeclarationsKt.isStaticMethodOfClass(irFunction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrDeclarationWithName getExportCandidate(IrDeclaration irDeclaration) {
        if (!(irDeclaration instanceof IrDeclarationWithVisibility) || !(irDeclaration instanceof IrDeclarationWithName) || !((IrDeclarationWithVisibility) irDeclaration).getVisibility().isPublicAPI() || DescriptorBasedUtilsKt.isExpect(irDeclaration)) {
            return null;
        }
        if (irDeclaration instanceof IrSimpleFunction) {
            IrPropertySymbol correspondingPropertySymbol = ((IrSimpleFunction) irDeclaration).getCorrespondingPropertySymbol();
            IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
            if (owner != null) {
                if (Intrinsics.areEqual(owner.getGetter(), irDeclaration)) {
                    return owner;
                }
                return null;
            }
        }
        return (IrDeclarationWithName) irDeclaration;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean shouldDeclarationBeExportedImplicitlyOrExplicitly(IrDeclarationWithName irDeclarationWithName, JsIrBackendContext jsIrBackendContext) {
        return AnnotationUtilsKt.isJsImplicitExport(irDeclarationWithName) || shouldDeclarationBeExported(irDeclarationWithName, jsIrBackendContext);
    }

    private static final boolean shouldDeclarationBeExported(IrDeclarationWithName irDeclarationWithName, JsIrBackendContext jsIrBackendContext) {
        if ((irDeclarationWithName instanceof IrClass) && ((IrClass) irDeclarationWithName).getKind() == ClassKind.ENUM_ENTRY) {
            return false;
        }
        if (CollectionsKt.contains(jsIrBackendContext.getAdditionalExportedDeclarationNames(), IrUtilsKt.getFqNameWhenAvailable(irDeclarationWithName)) || jsIrBackendContext.getAdditionalExportedDeclarations().contains(irDeclarationWithName)) {
            return true;
        }
        IrDeclarationWithName irDeclarationWithName2 = irDeclarationWithName;
        if (AnnotationUtilsKt.isJsExportIgnore(irDeclarationWithName2)) {
            return false;
        }
        if (irDeclarationWithName instanceof IrOverridableDeclaration) {
            IrOverridableDeclaration irOverridableDeclaration = (IrOverridableDeclaration) irDeclarationWithName;
            if (!irOverridableDeclaration.getOverriddenSymbols().isEmpty()) {
                if (!isOverriddenExported(irOverridableDeclaration, jsIrBackendContext)) {
                    IrSimpleFunction irSimpleFunction = irDeclarationWithName instanceof IrSimpleFunction ? (IrSimpleFunction) irDeclarationWithName : null;
                    if ((irSimpleFunction == null || !IrUtilsKt.isMethodOfAny(irSimpleFunction)) && !isAllowedFakeOverriddenDeclaration(irOverridableDeclaration, jsIrBackendContext)) {
                        return false;
                    }
                }
                return true;
            }
        }
        if (AnnotationUtilsKt.isJsExport(irDeclarationWithName2)) {
            return true;
        }
        IrDeclarationParent parent = irDeclarationWithName.getParent();
        if (parent instanceof IrDeclarationWithName) {
            return shouldDeclarationBeExported((IrDeclarationWithName) parent, jsIrBackendContext);
        }
        if (parent instanceof IrAnnotationContainer) {
            return AnnotationUtilsKt.isJsExport((IrAnnotationContainer) parent);
        }
        return false;
    }

    public static final boolean isAllowedFakeOverriddenDeclaration(final IrOverridableDeclaration<?> irOverridableDeclaration, final JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(irOverridableDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        IrOverridableDeclaration irOverridableDeclarationResolveFakeOverrideOrNull = irOverridableDeclaration.getIsFakeOverride() ? IrFakeOverrideUtilsKt.resolveFakeOverrideOrNull(irOverridableDeclaration, true, new Function1<?, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt$isAllowedFakeOverriddenDeclaration$firstExportedRealOverride$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrOverridableDeclaration<?> it) {
                IrClass parentClassOrNull;
                Intrinsics.checkNotNullParameter(it, "it");
                boolean z = true;
                if (it != irOverridableDeclaration && (parentClassOrNull = IrUtilsKt.getParentClassOrNull(it)) != null && ExportModelGeneratorKt.isExported(parentClassOrNull, context)) {
                    z = false;
                }
                return Boolean.valueOf(z);
            }
        }) : null;
        if (IrJsUtilsKt.isExportedInterface(irOverridableDeclarationResolveFakeOverrideOrNull != null ? IrUtilsKt.getParentClassOrNull(irOverridableDeclarationResolveFakeOverrideOrNull) : null, context)) {
            return true;
        }
        Sequence sequenceFilter = SequencesKt.filter(SequencesKt.map(CollectionsKt.asSequence(irOverridableDeclaration.getOverriddenSymbols()), new Function1<IrSymbol, IrSymbolOwner>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt.isAllowedFakeOverriddenDeclaration.1
            @Override // kotlin.jvm.functions.Function1
            public final IrSymbolOwner invoke(IrSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getOwner();
            }
        }), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt$isAllowedFakeOverriddenDeclaration$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof IrOverridableDeclaration);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        Iterator it = SequencesKt.map(SequencesKt.mapNotNull(SequencesKt.filter(sequenceFilter, new Function1<IrOverridableDeclaration<?>, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt.isAllowedFakeOverriddenDeclaration.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrOverridableDeclaration<?> it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(it2.getOverriddenSymbols().isEmpty());
            }
        }), new Function1<IrOverridableDeclaration<?>, IrClass>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt.isAllowedFakeOverriddenDeclaration.3
            @Override // kotlin.jvm.functions.Function1
            public final IrClass invoke(IrOverridableDeclaration<?> it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return IrUtilsKt.getParentClassOrNull(it2);
            }
        }), new Function1<IrClass, IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt.isAllowedFakeOverriddenDeclaration.4
            @Override // kotlin.jvm.functions.Function1
            public final IrClassSymbol invoke(IrClass it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return it2.getSymbol();
            }
        }).iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual((IrClassSymbol) it.next(), context.getIrBuiltIns().getEnumClass())) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isOverriddenExported(IrOverridableDeclaration<?> irOverridableDeclaration, JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(irOverridableDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Iterable overriddenSymbols = irOverridableDeclaration.getOverriddenSymbols();
        if ((overriddenSymbols instanceof Collection) && ((Collection) overriddenSymbols).isEmpty()) {
            return false;
        }
        Iterator it = overriddenSymbols.iterator();
        while (it.hasNext()) {
            IrSymbolOwner owner = ((IrSymbol) it.next()).getOwner();
            Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName");
            if (shouldDeclarationBeExported((IrDeclarationWithName) owner, context)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isExported(IrDeclaration irDeclaration, JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        IrDeclarationWithName exportCandidate = getExportCandidate(irDeclaration);
        if (exportCandidate == null) {
            return false;
        }
        return shouldDeclarationBeExported(exportCandidate, context);
    }

    public static final boolean isExportedImplicitlyOrExplicitly(IrDeclaration irDeclaration, JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        IrDeclarationWithName exportCandidate = getExportCandidate(irDeclaration);
        if (exportCandidate == null) {
            return false;
        }
        return shouldDeclarationBeExportedImplicitlyOrExplicitly(exportCandidate, context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ExportedVisibility toExportedVisibility(DescriptorVisibility descriptorVisibility) {
        return Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.PROTECTED) ? ExportedVisibility.PROTECTED : ExportedVisibility.DEFAULT;
    }

    static {
        Set<String> of = SetsKt.setOf((Object[]) new String[]{PsiKeyword.BREAK, PsiKeyword.CASE, PsiKeyword.CATCH, "class", PsiKeyword.CONST, PsiKeyword.CONTINUE, "debugger", "default", "delete", PsiKeyword.DO, PsiKeyword.ELSE, PsiKeyword.ENUM, "export", PsiKeyword.EXTENDS, "false", PsiKeyword.FINALLY, PsiKeyword.FOR, "function", PsiKeyword.IF, PsiKeyword.IMPORT, "in", PsiKeyword.INSTANCEOF, PsiKeyword.NEW, "null", "return", PsiKeyword.SUPER, PsiKeyword.SWITCH, "this", PsiKeyword.THROW, "true", PsiKeyword.TRY, "typeof", PsiKeyword.VAR, PsiKeyword.VOID, PsiKeyword.WHILE, "with"});
        reservedWords = of;
        Set<String> of2 = SetsKt.setOf((Object[]) new String[]{"as", PsiKeyword.IMPLEMENTS, PsiKeyword.INTERFACE, "let", "package", "private", "protected", "public", "static", PsiKeyword.YIELD});
        strictModeReservedWords = of2;
        allReservedWords = SetsKt.plus((Set) of, (Iterable) of2);
    }

    public static final Set<String> getStrictModeReservedWords() {
        return strictModeReservedWords;
    }
}
