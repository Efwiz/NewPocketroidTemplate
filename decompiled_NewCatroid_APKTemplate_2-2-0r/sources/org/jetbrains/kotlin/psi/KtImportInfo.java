package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: KtImportInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0001\u0016J\n\u0010\u0015\u001a\u0004\u0018\u00010\u0003H\u0002R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u0004\u0018\u00010\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0014ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0017À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtImportInfo;", "", "aliasName", "", "getAliasName", "()Ljava/lang/String;", "importContent", "Lorg/jetbrains/kotlin/psi/KtImportInfo$ImportContent;", "getImportContent", "()Lorg/jetbrains/kotlin/psi/KtImportInfo$ImportContent;", "importedFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getImportedFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "importedName", "Lorg/jetbrains/kotlin/name/Name;", "getImportedName", "()Lorg/jetbrains/kotlin/name/Name;", "isAllUnder", "", "()Z", "computeNameAsString", "ImportContent", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtImportInfo {
    String getAliasName();

    ImportContent getImportContent();

    FqName getImportedFqName();

    boolean isAllUnder();

    /* JADX INFO: compiled from: KtImportInfo.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtImportInfo$ImportContent;", "", "()V", "ExpressionBased", "FqNameBased", "Lorg/jetbrains/kotlin/psi/KtImportInfo$ImportContent$ExpressionBased;", "Lorg/jetbrains/kotlin/psi/KtImportInfo$ImportContent$FqNameBased;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class ImportContent {
        public /* synthetic */ ImportContent(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: KtImportInfo.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtImportInfo$ImportContent$ExpressionBased;", "Lorg/jetbrains/kotlin/psi/KtImportInfo$ImportContent;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "(Lorg/jetbrains/kotlin/psi/KtExpression;)V", "getExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ExpressionBased extends ImportContent {
            private final KtExpression expression;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public ExpressionBased(KtExpression expression) {
                super(null);
                Intrinsics.checkNotNullParameter(expression, "expression");
                this.expression = expression;
            }

            public final KtExpression getExpression() {
                return this.expression;
            }
        }

        private ImportContent() {
        }

        /* JADX INFO: compiled from: KtImportInfo.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtImportInfo$ImportContent$FqNameBased;", "Lorg/jetbrains/kotlin/psi/KtImportInfo$ImportContent;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/name/FqName;)V", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class FqNameBased extends ImportContent {
            private final FqName fqName;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public FqNameBased(FqName fqName) {
                super(null);
                Intrinsics.checkNotNullParameter(fqName, "fqName");
                this.fqName = fqName;
            }

            public final FqName getFqName() {
                return this.fqName;
            }
        }
    }

    default Name getImportedName() {
        String strComputeNameAsString = computeNameAsString();
        if (strComputeNameAsString == null) {
            return null;
        }
        if (strComputeNameAsString.length() <= 0) {
            strComputeNameAsString = null;
        }
        if (strComputeNameAsString != null) {
            return Name.identifier(strComputeNameAsString);
        }
        return null;
    }

    private default String computeNameAsString() {
        Name nameShortName;
        if (isAllUnder()) {
            return null;
        }
        String aliasName = getAliasName();
        if (aliasName != null) {
            return aliasName;
        }
        ImportContent importContent = getImportContent();
        if (importContent instanceof ImportContent.ExpressionBased) {
            KtSimpleNameExpression lastReference = KtPsiUtil.getLastReference(((ImportContent.ExpressionBased) importContent).getExpression());
            if (lastReference != null) {
                return lastReference.getReferencedName();
            }
            return null;
        }
        if (!(importContent instanceof ImportContent.FqNameBased)) {
            if (importContent == null) {
                return null;
            }
            throw new NoWhenBranchMatchedException();
        }
        FqName fqName = ((ImportContent.FqNameBased) importContent).getFqName();
        if (fqName.isRoot()) {
            fqName = null;
        }
        if (fqName == null || (nameShortName = fqName.shortName()) == null) {
            return null;
        }
        return nameShortName.asString();
    }
}
