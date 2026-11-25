package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrProperty;

/* JADX INFO: compiled from: PropertyLazyInitLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0007H\u0002J\u0018\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000e2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/RemoveInitializersForLazyProperties;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "fileToInitializerPureness", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "", "getFileToInitializerPureness", "()Ljava/util/Map;", "calculateFileFieldsPureness", "file", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RemoveInitializersForLazyProperties implements DeclarationTransformer {
    private final JsCommonBackendContext context;

    public RemoveInitializersForLazyProperties(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    private final Map<IrFile, Boolean> getFileToInitializerPureness() {
        return this.context.getPropertyLazyInitialization().getFileToInitializerPureness();
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        IrProperty correspondingProperty;
        IrField backingField;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!this.context.getPropertyLazyInitialization().getEnabled() || !(declaration instanceof IrField) || !PropertyLazyInitLoweringKt.isCompatibleDeclaration(declaration, this.context)) {
            return null;
        }
        IrDeclarationParent parent = declaration.getParent();
        IrFile irFile = parent instanceof IrFile ? (IrFile) parent : null;
        if (irFile == null || Intrinsics.areEqual((Object) getFileToInitializerPureness().get(irFile), (Object) true)) {
            return null;
        }
        Boolean bool = getFileToInitializerPureness().get(irFile);
        if (!(bool != null ? bool.booleanValue() : calculateFileFieldsPureness(irFile)) && (correspondingProperty = PropertyLazyInitLoweringKt.getCorrespondingProperty(declaration)) != null) {
            if (!PropertyLazyInitLoweringKt.isForLazyInit(correspondingProperty)) {
                correspondingProperty = null;
            }
            if (correspondingProperty != null && (backingField = correspondingProperty.getBackingField()) != null) {
                backingField.setInitializer(null);
            }
        }
        return null;
    }

    private final boolean calculateFileFieldsPureness(IrFile file) {
        boolean zAllFieldsInFilePure = PropertyLazyInitLoweringKt.allFieldsInFilePure(PropertyLazyInitLoweringKt.calculateFieldToExpression(CollectionsKt.toList(file.getDeclarations()), this.context).values());
        getFileToInitializerPureness().put(file, Boolean.valueOf(zAllFieldsInFilePure));
        return zAllFieldsInFilePure;
    }
}
