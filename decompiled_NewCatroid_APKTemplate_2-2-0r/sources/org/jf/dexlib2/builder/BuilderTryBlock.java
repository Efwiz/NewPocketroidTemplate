package org.jf.dexlib2.builder;

import java.util.List;
import org.jf.dexlib2.base.BaseTryBlock;
import org.jf.dexlib2.iface.reference.TypeReference;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public class BuilderTryBlock extends BaseTryBlock<BuilderExceptionHandler> {
    public final Label end;
    public final BuilderExceptionHandler exceptionHandler;
    public final Label start;

    public BuilderTryBlock(Label label, Label label2, String str, Label label3) {
        this.start = label;
        this.end = label2;
        this.exceptionHandler = BuilderExceptionHandler.newExceptionHandler(str, label3);
    }

    public BuilderTryBlock(Label label, Label label2, TypeReference typeReference, Label label3) {
        this.start = label;
        this.end = label2;
        this.exceptionHandler = BuilderExceptionHandler.newExceptionHandler(typeReference, label3);
    }

    public BuilderTryBlock(Label label, Label label2, Label label3) {
        this.start = label;
        this.end = label2;
        this.exceptionHandler = BuilderExceptionHandler.newExceptionHandler(label3);
    }

    @Override // org.jf.dexlib2.iface.TryBlock
    public int getStartCodeAddress() {
        return this.start.getCodeAddress();
    }

    @Override // org.jf.dexlib2.iface.TryBlock
    public int getCodeUnitCount() {
        return this.end.getCodeAddress() - this.start.getCodeAddress();
    }

    @Override // org.jf.dexlib2.iface.TryBlock
    public List<? extends BuilderExceptionHandler> getExceptionHandlers() {
        return ListUtil.newArrayList(this.exceptionHandler);
    }
}
