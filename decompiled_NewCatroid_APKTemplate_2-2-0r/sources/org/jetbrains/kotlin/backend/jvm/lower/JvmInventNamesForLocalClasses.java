package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.InventNamesForLocalClasses;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: JvmInventNamesForLocalClasses.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\nH\u0014J\u0010\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\nH\u0014R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmInventNamesForLocalClasses;", "Lorg/jetbrains/kotlin/backend/common/lower/InventNamesForLocalClasses;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "generateNamesForRegeneratedObjects", "", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Z)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "computeTopLevelClassName", "", "clazz", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "putLocalClassName", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "localClassName", "sanitizeNameIfNeeded", "name", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class JvmInventNamesForLocalClasses extends InventNamesForLocalClasses {
    private final JvmBackendContext context;

    public /* synthetic */ JvmInventNamesForLocalClasses(JvmBackendContext jvmBackendContext, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(jvmBackendContext, (i & 2) != 0 ? false : z);
    }

    protected final JvmBackendContext getContext() {
        return this.context;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmInventNamesForLocalClasses(JvmBackendContext context, boolean z) {
        super(true, z);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.InventNamesForLocalClasses
    protected String computeTopLevelClassName(IrClass clazz) {
        FqName fileClassFqName;
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        IrDeclarationParent parent = clazz.getParent();
        IrFile irFile = parent instanceof IrFile ? (IrFile) parent : null;
        if (irFile == null) {
            throw new AssertionError("Top-level class expected: " + RenderIrElementKt.render$default(clazz, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        if (Intrinsics.areEqual(clazz.getOrigin(), IrDeclarationOrigin.FILE_CLASS.INSTANCE) || Intrinsics.areEqual(clazz.getOrigin(), IrDeclarationOrigin.SYNTHETIC_FILE_CLASS.INSTANCE)) {
            fileClassFqName = FileClassLoweringKt.getFileClassInfo(irFile).getFileClassFqName();
        } else {
            fileClassFqName = irFile.getPackageFqName().child(clazz.getName());
            Intrinsics.checkNotNull(fileClassFqName);
        }
        String internalName = JvmClassName.byFqNameWithoutInnerClasses(fileClassFqName).getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        return internalName;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.InventNamesForLocalClasses
    protected String sanitizeNameIfNeeded(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        String strSanitizeNameIfNeeded = JvmCodegenUtil.sanitizeNameIfNeeded(name2, this.context.getState().getLanguageVersionSettings());
        Intrinsics.checkNotNullExpressionValue(strSanitizeNameIfNeeded, "sanitizeNameIfNeeded(...)");
        return strSanitizeNameIfNeeded;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.InventNamesForLocalClasses
    protected void putLocalClassName(IrAttributeContainer declaration, String localClassName) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(localClassName, "localClassName");
        JvmBackendContext jvmBackendContext = this.context;
        Type objectType = Type.getObjectType(localClassName);
        Intrinsics.checkNotNullExpressionValue(objectType, "getObjectType(...)");
        jvmBackendContext.putLocalClassType(declaration, objectType);
    }
}
