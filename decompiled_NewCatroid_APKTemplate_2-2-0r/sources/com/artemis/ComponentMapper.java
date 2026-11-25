package com.artemis;

import com.artemis.Component;
import com.artemis.annotations.DelayedComponentRemoval;
import com.artemis.utils.Bag;
import com.artemis.utils.reflect.ClassReflection;

/* JADX INFO: loaded from: classes8.dex */
public class ComponentMapper<A extends Component> extends BaseComponentMapper<A> {
    final Bag<A> components;
    private final EntityTransmuter createTransmuter;
    private final ComponentPool pool;
    private final ComponentRemover<A> purgatory;
    private final EntityTransmuter removeTransmuter;

    public ComponentMapper(Class<A> cls, World world) {
        super(world.getComponentManager().typeFactory.getTypeFor((Class<? extends Component>) cls));
        Bag<A> bag = new Bag<>(cls);
        this.components = bag;
        ComponentPool componentPool = this.type.isPooled ? new ComponentPool(cls) : null;
        this.pool = componentPool;
        if (world.isAlwaysDelayComponentRemoval() || ClassReflection.isAnnotationPresent(cls, DelayedComponentRemoval.class)) {
            this.purgatory = new DelayedComponentRemover(bag, componentPool, world.batchProcessor);
        } else {
            this.purgatory = new ImmediateComponentRemover(bag, componentPool);
        }
        this.createTransmuter = new EntityTransmuterFactory(world).add(cls).build();
        this.removeTransmuter = new EntityTransmuterFactory(world).remove(cls).build();
    }

    @Override // com.artemis.BaseComponentMapper
    public A get(int i) {
        return this.components.get(i);
    }

    @Override // com.artemis.BaseComponentMapper
    public boolean has(int i) {
        return (get(i) == null || this.purgatory.has(i)) ? false : true;
    }

    @Override // com.artemis.BaseComponentMapper
    public void remove(int i) {
        if (get(i) != null) {
            this.removeTransmuter.transmuteNoOperation(i);
            this.purgatory.mark(i);
        }
    }

    @Override // com.artemis.BaseComponentMapper
    protected void internalRemove(int i) {
        if (get(i) != null) {
            this.purgatory.mark(i);
        }
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
    @Override // com.artemis.BaseComponentMapper
    public A create(int i) {
        A a = (A) get(i);
        if (a != null && !this.purgatory.unmark(i)) {
            return a;
        }
        this.createTransmuter.transmuteNoOperation(i);
        A a2 = (A) createNew();
        this.components.unsafeSet(i, a2);
        return a2;
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
    @Override // com.artemis.BaseComponentMapper
    public A internalCreate(int i) {
        A a = (A) get(i);
        if (a != null && !this.purgatory.unmark(i)) {
            return a;
        }
        A a2 = (A) createNew();
        this.components.unsafeSet(i, a2);
        return a2;
    }

    private A createNew() {
        ComponentPool componentPool = this.pool;
        if (componentPool != null) {
            return componentPool.obtain();
        }
        return (A) ComponentManager.newInstance(this.type.getType());
    }
}
