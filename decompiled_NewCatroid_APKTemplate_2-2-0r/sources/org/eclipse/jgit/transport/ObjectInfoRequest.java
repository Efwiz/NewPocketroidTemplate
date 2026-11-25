package org.eclipse.jgit.transport;

import java.util.Collections;
import java.util.List;
import org.eclipse.jgit.lib.ObjectId;

/* JADX INFO: loaded from: classes13.dex */
public final class ObjectInfoRequest {
    private final List<ObjectId> objectIDs;

    private ObjectInfoRequest(List<ObjectId> list) {
        this.objectIDs = list;
    }

    /* synthetic */ ObjectInfoRequest(List list, ObjectInfoRequest objectInfoRequest) {
        this(list);
    }

    public List<ObjectId> getObjectIDs() {
        return this.objectIDs;
    }

    public static Builder builder() {
        return new Builder(null);
    }

    public static final class Builder {
        private List<ObjectId> objectIDs;

        private Builder() {
            this.objectIDs = Collections.emptyList();
        }

        /* synthetic */ Builder(Builder builder) {
            this();
        }

        public Builder setObjectIDs(List<ObjectId> list) {
            this.objectIDs = list;
            return this;
        }

        public ObjectInfoRequest build() {
            return new ObjectInfoRequest(Collections.unmodifiableList(this.objectIDs), null);
        }
    }
}
