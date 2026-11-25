package org.jetbrains.kotlin.konan.target;

import kotlin.Metadata;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: compiled from: TargetManager.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\n\u001a\u00020\u000bH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/TargetManager;", "", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "getTarget", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "targetName", "", "getTargetName", "()Ljava/lang/String;", SchemaSymbols.ATTVAL_LIST, "", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface TargetManager {
    KonanTarget getTarget();

    String getTargetName();

    void list();
}
