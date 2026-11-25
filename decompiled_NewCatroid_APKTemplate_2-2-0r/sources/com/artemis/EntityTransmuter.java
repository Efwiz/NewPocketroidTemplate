package com.artemis;

import com.artemis.utils.Bag;
import com.artemis.utils.BitVector;
import com.artemis.utils.ShortBag;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes8.dex */
public final class EntityTransmuter {
    private final BatchChangeProcessor batchProcessor;
    private final EntityManager em;
    private final ShortBag entityToIdentity;
    private final Factory factory;
    private final Bag<TransmuteOperation> operations;

    public EntityTransmuter(World world, Aspect.Builder builder) {
        this(world, world.getAspectSubscriptionManager().get(builder).getAspect());
    }

    EntityTransmuter(World world, Aspect aspect) {
        this(world, new BitVector(aspect.allSet), new BitVector(aspect.exclusionSet));
    }

    EntityTransmuter(World world, BitVector bitVector, BitVector bitVector2) {
        this.em = world.getEntityManager();
        this.entityToIdentity = world.getComponentManager().entityToIdentity;
        this.batchProcessor = world.batchProcessor;
        this.operations = new Bag<>(TransmuteOperation.class);
        this.factory = new Factory(world, bitVector, bitVector2);
    }

    public void transmute(int i) {
        if (isValid(i)) {
            TransmuteOperation operation = getOperation(i);
            operation.perform(i);
            this.entityToIdentity.unsafeSet(i, operation.compositionId);
        }
    }

    void transmuteNoOperation(int i) {
        if (isValid(i)) {
            this.entityToIdentity.unsafeSet(i, getOperation(i).compositionId);
        }
    }

    private boolean isValid(int i) {
        if (!this.em.isActive(i)) {
            throw new RuntimeException("Issued transmute on deleted " + i);
        }
        if (this.batchProcessor.isDeleted(i)) {
            return false;
        }
        this.batchProcessor.changed.unsafeSet(i);
        return true;
    }

    public void transmute(Entity entity) {
        transmute(entity.f80id);
    }

    TransmuteOperation getOperation(int i) {
        return operation(i, this.entityToIdentity.get(i));
    }

    private TransmuteOperation operation(int i, int i2) {
        TransmuteOperation transmuteOperationSafeGet = this.operations.safeGet(i2);
        if (transmuteOperationSafeGet != null) {
            return transmuteOperationSafeGet;
        }
        TransmuteOperation transmuteOperationCreateOperation = this.factory.createOperation(i);
        this.operations.set(i2, transmuteOperationCreateOperation);
        return transmuteOperationCreateOperation;
    }

    public String toString() {
        return "EntityTransmuter(add=" + this.factory.additions + " remove=" + this.factory.removals + Constant.AFTER_QUTO;
    }

    static class Factory {
        private final BitVector additions;
        private final BitVector bs = new BitVector();
        private final ComponentManager cm;
        private final BitVector removals;

        Factory(World world, BitVector bitVector, BitVector bitVector2) {
            this.cm = world.getComponentManager();
            this.additions = bitVector;
            this.removals = bitVector2;
        }

        TransmuteOperation createOperation(int i) {
            BitVector bitVectorComponentBits = this.cm.componentBits(i);
            this.bs.clear();
            this.bs.or(bitVectorComponentBits);
            this.bs.or(this.additions);
            this.bs.andNot(this.removals);
            return new TransmuteOperation(this.cm.compositionIdentity(this.bs), getAdditions(bitVectorComponentBits), getRemovals(bitVectorComponentBits));
        }

        private Bag<ComponentMapper> getAdditions(BitVector bitVector) {
            ComponentTypeFactory componentTypeFactory = this.cm.typeFactory;
            Bag<ComponentMapper> bag = new Bag<>((Class<ComponentMapper>) ComponentMapper.class);
            int iNextSetBit = this.additions.nextSetBit(0);
            while (iNextSetBit >= 0) {
                if (!bitVector.get(iNextSetBit)) {
                    bag.add(this.cm.getMapper(componentTypeFactory.getTypeFor(iNextSetBit).getType()));
                }
                iNextSetBit = this.additions.nextSetBit(iNextSetBit + 1);
            }
            return bag;
        }

        private Bag<ComponentMapper> getRemovals(BitVector bitVector) {
            ComponentTypeFactory componentTypeFactory = this.cm.typeFactory;
            Bag<ComponentMapper> bag = new Bag<>((Class<ComponentMapper>) ComponentMapper.class);
            int iNextSetBit = this.removals.nextSetBit(0);
            while (iNextSetBit >= 0) {
                if (bitVector.get(iNextSetBit)) {
                    bag.add(this.cm.getMapper(componentTypeFactory.getTypeFor(iNextSetBit).getType()));
                }
                iNextSetBit = this.removals.nextSetBit(iNextSetBit + 1);
            }
            return bag;
        }
    }

    static class TransmuteOperation {
        private final ComponentMapper[] additions;
        public final short compositionId;
        private final ComponentMapper[] removals;

        public TransmuteOperation(int i, ComponentMapper[] componentMapperArr, ComponentMapper[] componentMapperArr2) {
            this.compositionId = (short) i;
            this.additions = componentMapperArr;
            this.removals = componentMapperArr2;
        }

        public TransmuteOperation(int i, Bag<ComponentMapper> bag, Bag<ComponentMapper> bag2) {
            this.compositionId = (short) i;
            this.additions = new ComponentMapper[bag.size()];
            this.removals = new ComponentMapper[bag2.size()];
            int size = bag.size();
            for (int i2 = 0; size > i2; i2++) {
                this.additions[i2] = bag.get(i2);
            }
            int size2 = bag2.size();
            for (int i3 = 0; size2 > i3; i3++) {
                this.removals[i3] = bag2.get(i3);
            }
        }

        public void perform(int i) {
            int length = this.additions.length;
            for (int i2 = 0; length > i2; i2++) {
                this.additions[i2].internalCreate(i);
            }
            int length2 = this.removals.length;
            for (int i3 = 0; length2 > i3; i3++) {
                this.removals[i3].internalRemove(i);
            }
        }

        Bag<Class<? extends Component>> getAdditions(Bag<Class<? extends Component>> bag) {
            int length = this.additions.length;
            for (int i = 0; length > i; i++) {
                bag.add(this.additions[i].getType().getType());
            }
            return bag;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("TransmuteOperation(");
            int i = 0;
            String str = "";
            if (this.additions.length > 0) {
                sb.append("add={");
                ComponentMapper[] componentMapperArr = this.additions;
                int length = componentMapperArr.length;
                int i2 = 0;
                String str2 = "";
                while (i2 < length) {
                    sb.append(str2).append(componentMapperArr[i2].getType().getType().getSimpleName());
                    i2++;
                    str2 = ", ";
                }
                sb.append("}");
            }
            if (this.removals.length > 0) {
                if (this.additions.length > 0) {
                    sb.append(" ");
                }
                sb.append("remove={");
                ComponentMapper[] componentMapperArr2 = this.removals;
                int length2 = componentMapperArr2.length;
                while (i < length2) {
                    sb.append(str).append(componentMapperArr2[i].getType().getType().getSimpleName());
                    i++;
                    str = ", ";
                }
                sb.append("}");
            }
            sb.append(Constant.AFTER_QUTO);
            return sb.toString();
        }
    }
}
