package com.artemis;

import com.artemis.utils.Bag;
import com.artemis.utils.BitVector;

/* JADX INFO: loaded from: classes8.dex */
public class ArchetypeBuilder {
    private final Bag<Class<? extends Component>> classes;

    public ArchetypeBuilder(Archetype archetype) {
        Bag<Class<? extends Component>> bag = new Bag<>();
        this.classes = bag;
        if (archetype == null) {
            return;
        }
        archetype.transmuter.getAdditions(bag);
    }

    public ArchetypeBuilder() {
        this(null);
    }

    public ArchetypeBuilder add(Class<? extends Component> cls) {
        if (!this.classes.contains(cls)) {
            this.classes.add(cls);
        }
        return this;
    }

    public ArchetypeBuilder add(Class<? extends Component>... clsArr) {
        for (int i = 0; clsArr.length > i; i++) {
            Class<? extends Component> cls = clsArr[i];
            if (!this.classes.contains(cls)) {
                this.classes.add(cls);
            }
        }
        return this;
    }

    public ArchetypeBuilder remove(Class<? extends Component> cls) {
        this.classes.remove(cls);
        return this;
    }

    public ArchetypeBuilder remove(Class<? extends Component>... clsArr) {
        for (int i = 0; clsArr.length > i; i++) {
            this.classes.remove(clsArr[i]);
        }
        return this;
    }

    public Archetype build(World world) {
        return build(world, null);
    }

    public Archetype build(World world, String str) {
        ComponentType[] componentTypeArrResolveTypes = resolveTypes(world);
        ComponentManager componentManager = world.getComponentManager();
        int length = componentTypeArrResolveTypes.length;
        ComponentMapper[] componentMapperArr = new ComponentMapper[length];
        for (int i = 0; length > i; i++) {
            componentMapperArr[i] = componentManager.getMapper(componentTypeArrResolveTypes[i].getType());
        }
        int iCompositionIdentity = componentManager.compositionIdentity(bitset(componentTypeArrResolveTypes));
        if (str == null || str.isEmpty()) {
            str = String.valueOf(iCompositionIdentity);
        }
        return new Archetype(new EntityTransmuter.TransmuteOperation(iCompositionIdentity, componentMapperArr, new ComponentMapper[0]), iCompositionIdentity, str);
    }

    private static BitVector bitset(ComponentType[] componentTypeArr) {
        BitVector bitVector = new BitVector();
        for (int i = 0; componentTypeArr.length > i; i++) {
            bitVector.set(componentTypeArr[i].getIndex());
        }
        return bitVector;
    }

    private ComponentType[] resolveTypes(World world) {
        ComponentTypeFactory componentTypeFactory = world.getComponentManager().typeFactory;
        ComponentType[] componentTypeArr = new ComponentType[this.classes.size()];
        int size = this.classes.size();
        for (int i = 0; size > i; i++) {
            componentTypeArr[i] = componentTypeFactory.getTypeFor(this.classes.get(i));
        }
        return componentTypeArr;
    }
}
