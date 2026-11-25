package com.artemis;

import com.artemis.annotations.SkipWire;
import com.artemis.utils.Bag;
import com.artemis.utils.BitVector;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.IntBag;
import com.artemis.utils.ShortBag;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.ReflectionException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes8.dex */
@SkipWire
public class ComponentManager extends BaseSystem {
    static final int NO_COMPONENTS = 0;
    final ShortBag entityToIdentity;
    protected final ComponentTypeFactory typeFactory;
    private Bag<ComponentMapper> mappers = new Bag<>(ComponentMapper.class);
    private final ComponentIdentityResolver identityResolver = new ComponentIdentityResolver();

    @Override // com.artemis.BaseSystem
    protected void processSystem() {
    }

    protected ComponentManager(int i) {
        this.entityToIdentity = new ShortBag(i);
        this.typeFactory = new ComponentTypeFactory(this, i);
    }

    protected <T extends Component> T create(int i, Class<T> cls) {
        return (T) getMapper(cls).create(i);
    }

    protected <T extends Component> ComponentMapper<T> getMapper(Class<T> cls) {
        return this.mappers.get(this.typeFactory.getTypeFor((Class<? extends Component>) cls).getIndex());
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    void registerComponentType(ComponentType componentType, int i) {
        int index = componentType.getIndex();
        ComponentMapper componentMapper = new ComponentMapper(componentType.getType(), this.world);
        componentMapper.components.ensureCapacity(i);
        this.mappers.set(index, componentMapper);
    }

    static <T extends Component> T newInstance(Class<T> cls) {
        try {
            return (T) ClassReflection.newInstance(cls);
        } catch (ReflectionException e) {
            throw new InvalidComponentException(cls, "Unable to instantiate component.", e);
        }
    }

    void clean(IntBag intBag) {
        int[] data = intBag.getData();
        int size = intBag.size();
        for (int i = 0; size > i; i++) {
            removeComponents(data[i]);
        }
    }

    private void removeComponents(int i) {
        Bag<ComponentMapper> bagComponentMappers = componentMappers(i);
        int size = bagComponentMappers.size();
        for (int i2 = 0; size > i2; i2++) {
            bagComponentMappers.get(i2).internalRemove(i);
        }
        setIdentity(i, 0);
    }

    protected Bag<Component> getComponentsByType(ComponentType componentType) {
        return this.mappers.get(componentType.getIndex()).components;
    }

    public ImmutableBag<ComponentType> getComponentTypes() {
        return this.typeFactory.types;
    }

    protected Component getComponent(int i, ComponentType componentType) {
        return this.mappers.get(componentType.getIndex()).get(i);
    }

    public Bag<Component> getComponentsFor(int i, Bag<Component> bag) {
        Bag<ComponentMapper> bagComponentMappers = componentMappers(i);
        int size = bagComponentMappers.size();
        for (int i2 = 0; size > i2; i2++) {
            bag.add(bagComponentMappers.get(i2).get(i));
        }
        return bag;
    }

    BitVector componentBits(int i) {
        return this.identityResolver.compositionBits.get(this.entityToIdentity.get(i));
    }

    private Bag<ComponentMapper> componentMappers(int i) {
        return this.identityResolver.compositionMappers.get(this.entityToIdentity.get(i));
    }

    public int compositionIdentity(BitVector bitVector) {
        int identity = this.identityResolver.getIdentity(bitVector);
        if (identity != -1) {
            return identity;
        }
        int iAllocateIdentity = this.identityResolver.allocateIdentity(bitVector, this);
        this.world.getAspectSubscriptionManager().processComponentIdentity(iAllocateIdentity, bitVector);
        return iAllocateIdentity;
    }

    public int getIdentity(int i) {
        return this.entityToIdentity.get(i);
    }

    void synchronize(EntitySubscription entitySubscription) {
        Bag<BitVector> bag = this.identityResolver.compositionBits;
        int size = bag.size();
        for (int i = 1; size > i; i++) {
            entitySubscription.processComponentIdentity(i, bag.get(i));
        }
        for (Entity entity : this.world.getEntityManager().entities) {
            if (entity != null) {
                entitySubscription.check(entity.f80id, getIdentity(entity.f80id));
            }
        }
        entitySubscription.informEntityChanges();
        entitySubscription.rebuildCompressedActives();
    }

    void setIdentity(int i, int i2) {
        this.entityToIdentity.unsafeSet(i, (short) i2);
    }

    public ComponentTypeFactory getTypeFactory() {
        return this.typeFactory;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public void ensureCapacity(int i) {
        this.typeFactory.initialMapperCapacity = i;
        this.entityToIdentity.ensureCapacity(i);
        Iterator<ComponentMapper> it = this.mappers.iterator();
        while (it.hasNext()) {
            it.next().components.ensureCapacity(i);
        }
    }

    static final class ComponentIdentityResolver {
        final Bag<BitVector> compositionBits;
        final Bag<Bag<ComponentMapper>> compositionMappers;

        ComponentIdentityResolver() {
            Bag<BitVector> bag = new Bag<>((Class<BitVector>) BitVector.class);
            this.compositionBits = bag;
            bag.add(new BitVector());
            Bag<Bag<ComponentMapper>> bag2 = new Bag<>();
            this.compositionMappers = bag2;
            bag2.add(new Bag<>(ComponentMapper.class));
        }

        int getIdentity(BitVector bitVector) {
            BitVector[] data = this.compositionBits.getData();
            int size = this.compositionBits.size();
            for (int i = 0; size > i; i++) {
                if (bitVector.equals(data[i])) {
                    return i;
                }
            }
            return -1;
        }

        int allocateIdentity(BitVector bitVector, ComponentManager componentManager) {
            Bag<ComponentMapper> bag = new Bag<>(ComponentMapper.class, bitVector.cardinality());
            ComponentTypeFactory typeFactory = componentManager.getTypeFactory();
            int i = 0;
            while (true) {
                int iNextSetBit = bitVector.nextSetBit(i);
                if (iNextSetBit >= 0) {
                    bag.add(componentManager.getMapper(typeFactory.getTypeFor(iNextSetBit).getType()));
                    i = iNextSetBit + 1;
                } else {
                    this.compositionMappers.add(bag);
                    this.compositionBits.add(new BitVector(bitVector));
                    return this.compositionBits.size() - 1;
                }
            }
        }
    }
}
