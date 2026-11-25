package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.impl.IrFileImpl;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrFileSymbolImpl;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: MoveBodilessDeclarationsToSeparatePlace.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u0007H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t²\u0006\n\u0010\n\u001a\u00020\u000bX\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/MoveBodilessDeclarationsToSeparatePlaceLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "backend.js", "externalPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MoveBodilessDeclarationsToSeparatePlaceLowering implements DeclarationTransformer {
    private final JsIrBackendContext context;

    public MoveBodilessDeclarationsToSeparatePlaceLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrDeclarationParent parent = declaration.getParent();
        final IrFile irFile = parent instanceof IrFile ? (IrFile) parent : null;
        if (irFile == null) {
            return null;
        }
        Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<IrFile>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.MoveBodilessDeclarationsToSeparatePlaceLowering$transformFlat$externalPackageFragment$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrFile invoke() {
                Map<IrFileSymbol, IrFile> externalPackageFragment = this.this$0.context.getExternalPackageFragment();
                IrFileSymbol symbol = irFile.getSymbol();
                IrFile irFile2 = irFile;
                IrFileImpl irFileImpl = externalPackageFragment.get(symbol);
                if (irFileImpl == null) {
                    IrFileEntry fileEntry = irFile2.getFileEntry();
                    FqName packageFqName = irFile2.getPackageFqName();
                    IrFileImpl irFileImpl2 = new IrFileImpl(fileEntry, new IrFileSymbolImpl(null, 1, null), packageFqName, irFile2.getModule());
                    irFileImpl2.setAnnotations(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) irFileImpl2.getAnnotations(), (List) irFile2.getAnnotations()));
                    irFileImpl = irFileImpl2;
                    externalPackageFragment.put(symbol, irFileImpl);
                }
                return irFileImpl;
            }
        });
        IrFile irFile2 = irFile;
        if (AnnotationUtilsKt.getJsModule(irFile2) != null || AnnotationUtilsKt.getJsQualifier(irFile2) != null) {
            transformFlat$lambda$0(lazy).getDeclarations().add(declaration);
            declaration.setParent(transformFlat$lambda$0(lazy));
            this.context.getPackageLevelJsModules().add(transformFlat$lambda$0(lazy));
            return CollectionsKt.emptyList();
        }
        IrDeclarationWithName irDeclarationWithName = declaration instanceof IrDeclarationWithName ? (IrDeclarationWithName) declaration : null;
        if (irDeclarationWithName == null) {
            return null;
        }
        IrDeclarationWithName irDeclarationWithName2 = irDeclarationWithName;
        if (MoveBodilessDeclarationsToSeparatePlaceKt.isBuiltInClass(irDeclarationWithName2) || MoveBodilessDeclarationsToSeparatePlaceKt.isIntrinsic(irDeclarationWithName2)) {
            IrUtilsKt.addChild(this.context.getBodilessBuiltInsPackageFragment(), irDeclarationWithName2);
            return CollectionsKt.emptyList();
        }
        if (!IrUtilsKt.isEffectivelyExternal(irDeclarationWithName2)) {
            return null;
        }
        if (AnnotationUtilsKt.getJsModule(irDeclarationWithName) != null) {
            this.context.getDeclarationLevelJsModules().add(irDeclarationWithName);
        }
        transformFlat$lambda$0(lazy).getDeclarations().add(irDeclarationWithName);
        irDeclarationWithName.setParent(transformFlat$lambda$0(lazy));
        return CollectionsKt.emptyList();
    }

    private static final IrFile transformFlat$lambda$0(Lazy<? extends IrFile> lazy) {
        return lazy.getValue();
    }
}
