package org.jetbrains.kotlin.psi.stubs;

import com.reandroid.apk.PathMap;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: StubInterfaces.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u000bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/KotlinFileStub;", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/PsiFileStub;", "Lorg/jetbrains/kotlin/psi/KtFile;", "findImportsByAlias", "", "Lorg/jetbrains/kotlin/psi/stubs/KotlinImportDirectiveStub;", PathMap.NAME_alias, "", "getPackageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "isScript", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinFileStub extends PsiFileStub<KtFile> {
    List<KotlinImportDirectiveStub> findImportsByAlias(String alias);

    FqName getPackageFqName();

    /* JADX INFO: renamed from: isScript */
    boolean getIsScript();
}
