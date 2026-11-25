package com.artemis;

import com.artemis.utils.Bag;
import com.artemis.utils.BitVector;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: classes8.dex */
public class Aspect {
    BitVector allSet;
    BitVector exclusionSet;
    BitVector oneSet;

    private Aspect() {
        this.allSet = new BitVector();
        this.exclusionSet = new BitVector();
        this.oneSet = new BitVector();
    }

    public BitVector getAllSet() {
        return this.allSet;
    }

    public BitVector getExclusionSet() {
        return this.exclusionSet;
    }

    public BitVector getOneSet() {
        return this.oneSet;
    }

    public boolean isInterested(Entity entity) {
        return isInterested(entity.getComponentBits());
    }

    public boolean isInterested(BitVector bitVector) {
        if (!this.allSet.isEmpty() && !bitVector.containsAll(this.allSet)) {
            return false;
        }
        if (this.exclusionSet.isEmpty() || !this.exclusionSet.intersects(bitVector)) {
            return this.oneSet.isEmpty() || this.oneSet.intersects(bitVector);
        }
        return false;
    }

    public static Builder all() {
        return new Builder().all(new Class[0]);
    }

    @SafeVarargs
    public static Builder all(Class<? extends Component>... clsArr) {
        return new Builder().all(clsArr);
    }

    public static Builder all(Collection<Class<? extends Component>> collection) {
        return new Builder().all(collection);
    }

    @SafeVarargs
    public static Builder exclude(Class<? extends Component>... clsArr) {
        return new Builder().exclude(clsArr);
    }

    public static Builder exclude(Collection<Class<? extends Component>> collection) {
        return new Builder().exclude(collection);
    }

    @SafeVarargs
    public static Builder one(Class<? extends Component>... clsArr) {
        return new Builder().one(clsArr);
    }

    public static Builder one(Collection<Class<? extends Component>> collection) {
        return new Builder().one(collection);
    }

    public static class Builder {
        private final Bag<Class<? extends Component>> allTypes;
        private final Bag<Class<? extends Component>> exclusionTypes;
        private final Bag<Class<? extends Component>> oneTypes;

        private Builder() {
            this.allTypes = new Bag<>();
            this.exclusionTypes = new Bag<>();
            this.oneTypes = new Bag<>();
        }

        @SafeVarargs
        public final Builder all(Class<? extends Component>... clsArr) {
            for (Class<? extends Component> cls : clsArr) {
                this.allTypes.add(cls);
            }
            return this;
        }

        public Builder copy() {
            Builder builder = new Builder();
            builder.allTypes.addAll(this.allTypes);
            builder.exclusionTypes.addAll(this.exclusionTypes);
            builder.oneTypes.addAll(this.oneTypes);
            return builder;
        }

        public Builder all(Collection<Class<? extends Component>> collection) {
            Iterator<Class<? extends Component>> it = collection.iterator();
            while (it.hasNext()) {
                this.allTypes.add(it.next());
            }
            return this;
        }

        @SafeVarargs
        public final Builder one(Class<? extends Component>... clsArr) {
            for (Class<? extends Component> cls : clsArr) {
                this.oneTypes.add(cls);
            }
            return this;
        }

        public Builder one(Collection<Class<? extends Component>> collection) {
            Iterator<Class<? extends Component>> it = collection.iterator();
            while (it.hasNext()) {
                this.oneTypes.add(it.next());
            }
            return this;
        }

        @SafeVarargs
        public final Builder exclude(Class<? extends Component>... clsArr) {
            for (Class<? extends Component> cls : clsArr) {
                this.exclusionTypes.add(cls);
            }
            return this;
        }

        public Builder exclude(Collection<Class<? extends Component>> collection) {
            Iterator<Class<? extends Component>> it = collection.iterator();
            while (it.hasNext()) {
                this.exclusionTypes.add(it.next());
            }
            return this;
        }

        public Aspect build(World world) {
            ComponentTypeFactory componentTypeFactory = world.getComponentManager().typeFactory;
            Aspect aspect = new Aspect();
            associate(componentTypeFactory, this.allTypes, aspect.allSet);
            associate(componentTypeFactory, this.exclusionTypes, aspect.exclusionSet);
            associate(componentTypeFactory, this.oneTypes, aspect.oneSet);
            return aspect;
        }

        private static void associate(ComponentTypeFactory componentTypeFactory, Bag<Class<? extends Component>> bag, BitVector bitVector) {
            Iterator<Class<? extends Component>> it = bag.iterator();
            while (it.hasNext()) {
                bitVector.set(componentTypeFactory.getIndexFor(it.next()));
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Builder builder = (Builder) obj;
            return this.allTypes.equals(builder.allTypes) && this.exclusionTypes.equals(builder.exclusionTypes) && this.oneTypes.equals(builder.oneTypes);
        }

        public int hashCode() {
            return (((this.allTypes.hashCode() * 31) + this.exclusionTypes.hashCode()) * 31) + this.oneTypes.hashCode();
        }

        public String toString() {
            return "Aspect[all=" + ((Object) append(this.allTypes)) + ", one=" + ((Object) append(this.oneTypes)) + ", exclude=" + ((Object) append(this.exclusionTypes)) + ']';
        }

        private StringBuilder append(Bag<Class<? extends Component>> bag) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            Iterator<Class<? extends Component>> it = bag.iterator();
            String str = "";
            while (it.hasNext()) {
                sb.append(str).append(it.next().getSimpleName());
                str = ", ";
            }
            sb.append(Constant.AFTER_QUTO);
            return sb;
        }
    }
}
