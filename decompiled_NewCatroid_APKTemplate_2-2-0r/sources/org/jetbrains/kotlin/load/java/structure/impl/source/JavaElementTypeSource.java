package org.jetbrains.kotlin.load.java.structure.impl.source;

import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;

/* JADX INFO: compiled from: JavaElementTypeSource.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementTypeSource;", "TYPE", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "", "()V", "factory", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;", "getFactory", "()Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;", ModuleXmlParser.TYPE, "getType", "()Lcom/intellij/psi/PsiType;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class JavaElementTypeSource<TYPE extends PsiType> {
    public abstract JavaElementSourceFactory getFactory();

    public abstract TYPE getType();
}
