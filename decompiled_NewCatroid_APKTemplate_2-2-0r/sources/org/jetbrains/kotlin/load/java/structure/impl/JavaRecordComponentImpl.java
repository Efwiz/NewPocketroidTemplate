package org.jetbrains.kotlin.load.java.structure.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.load.java.structure.JavaRecordComponent;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory;

/* JADX INFO: compiled from: JavaRecordComponentImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/JavaRecordComponentImpl;", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaMemberImpl;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiRecordComponent;", "Lorg/jetbrains/kotlin/load/java/structure/JavaRecordComponent;", "psiRecordComponentSource", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;", "(Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;)V", "isVararg", "", "()Z", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getType", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaRecordComponentImpl extends JavaMemberImpl<PsiRecordComponent> implements JavaRecordComponent {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaRecordComponentImpl(JavaElementPsiSource<PsiRecordComponent> psiRecordComponentSource) {
        super(psiRecordComponentSource);
        Intrinsics.checkNotNullParameter(psiRecordComponentSource, "psiRecordComponentSource");
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaRecordComponent
    public JavaType getType() {
        PsiType type = ((PsiRecordComponent) getPsi()).getType();
        JavaElementSourceFactory sourceFactory = getSourceFactory();
        JavaElementPsiSource<Psi> psiElementSource = this.psiElementSource;
        Intrinsics.checkNotNullExpressionValue(psiElementSource, "psiElementSource");
        JavaTypeImpl<?> javaTypeImplCreate = JavaTypeImpl.create(type, sourceFactory.createVariableReturnTypeSource(psiElementSource));
        Intrinsics.checkNotNullExpressionValue(javaTypeImplCreate, "create(...)");
        return javaTypeImplCreate;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaRecordComponent
    /* JADX INFO: renamed from: isVararg */
    public boolean getIsVararg() {
        return ((PsiRecordComponent) getPsi()).isVarArgs();
    }
}
