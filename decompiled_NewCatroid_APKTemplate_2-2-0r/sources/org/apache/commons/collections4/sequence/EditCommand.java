package org.apache.commons.collections4.sequence;

/* JADX INFO: loaded from: classes12.dex */
public abstract class EditCommand<T> {
    private final T object;

    public abstract void accept(CommandVisitor<T> commandVisitor);

    protected EditCommand(T t) {
        this.object = t;
    }

    protected T getObject() {
        return this.object;
    }
}
