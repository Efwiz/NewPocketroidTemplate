package org.jetbrains.kotlin.backend.common;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.Iterator;
import java.util.LinkedHashSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.koin.core.instance.InstanceFactory;

/* JADX INFO: compiled from: IrValidator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0016\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b\u001a\n\u0010\t\u001a\u00020\u0001*\u00020\n\u001a&\u0010\u000b\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000e\u001a\u00020\nH\u0002¨\u0006\u000f"}, d2 = {"validateIrFile", "", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "validateIrModule", "irModule", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "checkDeclarationParents", "Lorg/jetbrains/kotlin/ir/IrElement;", "reportIrValidationError", CrashHianalyticsData.MESSAGE, "", "irElement", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrValidatorKt {
    public static final void validateIrFile(CommonBackendContext context, IrFile irFile) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        IrVisitorsKt.acceptVoid(irFile, new IrValidator(context, new IrValidatorConfig(false, false, false, false, false, false, 60, null)));
    }

    public static final void validateIrModule(CommonBackendContext context, IrModuleFragment irModule) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(irModule, "irModule");
        IrVisitorsKt.acceptVoid(irModule, new IrValidator(context, new IrValidatorConfig(false, true, false, false, false, false, 60, null)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void reportIrValidationError(CommonBackendContext commonBackendContext, String str, IrFile irFile, IrElement irElement) {
        try {
            UtilsKt.reportWarning(commonBackendContext, "[IR VALIDATION] " + str, irFile, irElement);
        } catch (Throwable th) {
            System.out.println((Object) ("an error trying to print a warning message: " + th));
            th.printStackTrace();
        }
    }

    public static final void checkDeclarationParents(IrElement irElement) {
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        CheckDeclarationParentsVisitor checkDeclarationParentsVisitor = new CheckDeclarationParentsVisitor();
        IrVisitorsKt.acceptVoid(irElement, checkDeclarationParentsVisitor);
        if (checkDeclarationParentsVisitor.getErrors().isEmpty()) {
            return;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        StringBuilder sb = new StringBuilder("Declarations with wrong parent: ");
        sb.append(checkDeclarationParentsVisitor.getErrors().size());
        sb.append("\n");
        Iterator<T> it = checkDeclarationParentsVisitor.getErrors().iterator();
        while (true) {
            String strRender$default = null;
            if (!it.hasNext()) {
                break;
            }
            CheckDeclarationParentsVisitor.Error error = (CheckDeclarationParentsVisitor.Error) it.next();
            sb.append("declaration: ");
            sb.append(RenderIrElementKt.render$default(error.getDeclaration(), (DumpIrTreeOptions) null, 1, (Object) null));
            sb.append(InstanceFactory.ERROR_SEPARATOR);
            sb.append(error.getDeclaration());
            sb.append("\nexpectedParent: ");
            sb.append(RenderIrElementKt.render$default(error.getExpectedParent(), (DumpIrTreeOptions) null, 1, (Object) null));
            sb.append("\nactualParent: ");
            IrDeclarationParent actualParent = error.getActualParent();
            if (actualParent != null) {
                strRender$default = RenderIrElementKt.render$default(actualParent, (DumpIrTreeOptions) null, 1, (Object) null);
            }
            sb.append(strRender$default);
            sb.append("\n");
            linkedHashSet.add(error.getExpectedParent());
        }
        sb.append("\nExpected parents:\n");
        Iterator it2 = linkedHashSet.iterator();
        while (it2.hasNext()) {
            sb.append(DumpIrTreeKt.dump$default((IrDeclarationParent) it2.next(), null, 1, null));
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        throw new AssertionError(string);
    }
}
