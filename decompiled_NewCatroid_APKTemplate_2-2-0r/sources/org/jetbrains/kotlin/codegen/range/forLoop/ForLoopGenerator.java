package org.jetbrains.kotlin.codegen.range.forLoop;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.org.objectweb.asm.Label;

/* JADX INFO: compiled from: ForLoopGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u0007H&J\b\u0010\u000b\u001a\u00020\u0007H&J\b\u0010\f\u001a\u00020\u0007H&J\b\u0010\r\u001a\u00020\u0007H&J\u0010\u0010\u000e\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&J\u0010\u0010\u000f\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/ForLoopGenerator;", "", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "getForExpression", "()Lorg/jetbrains/kotlin/psi/KtForExpression;", "afterBody", "", "loopExit", "Lorg/jetbrains/org/objectweb/asm/Label;", "afterLoop", "beforeBody", "beforeLoop", "body", "checkEmptyLoop", "checkPreCondition", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ForLoopGenerator {
    void afterBody(Label loopExit);

    void afterLoop();

    void beforeBody();

    void beforeLoop();

    void body();

    void checkEmptyLoop(Label loopExit);

    void checkPreCondition(Label loopExit);

    KtForExpression getForExpression();
}
