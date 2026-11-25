package org.jetbrains.kotlin.com.intellij.psi.stubs;

import java.lang.reflect.Field;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes3.dex */
final class StubFieldAccessor implements Supplier<ObjectStubSerializer<?, ? extends Stub>> {
    final String externalId;
    private final Field myField;
    private volatile ObjectStubSerializer<?, Stub> myFieldValue;

    @Override // java.util.function.Supplier
    public ObjectStubSerializer<?, ? extends Stub> get() {
        ObjectStubSerializer<?, Stub> objectStubSerializer = this.myFieldValue;
        if (objectStubSerializer == null) {
            try {
                objectStubSerializer = (ObjectStubSerializer) this.myField.get(null);
                this.myFieldValue = objectStubSerializer;
                if (!objectStubSerializer.getExternalId().equals(this.externalId)) {
                    throw new IllegalStateException("External id mismatch in " + this + ". Judging by extension declaration it should be " + this.externalId + " but " + objectStubSerializer.getExternalId() + " is returned.");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return objectStubSerializer;
    }

    public String toString() {
        return this.myField.toString();
    }
}
