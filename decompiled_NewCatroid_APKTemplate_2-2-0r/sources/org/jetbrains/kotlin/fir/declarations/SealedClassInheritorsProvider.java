package org.jetbrains.kotlin.fir.declarations;

import java.util.List;
import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: SealedClassInheritors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/SealedClassInheritorsProvider;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "getSealedClassInheritors", "", "Lorg/jetbrains/kotlin/name/ClassId;", "firClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SealedClassInheritorsProvider implements FirSessionComponent {
    public abstract List<ClassId> getSealedClassInheritors(FirRegularClass firClass);
}
