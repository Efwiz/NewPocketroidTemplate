package com.artemis;

import com.artemis.utils.BitVector;

/* JADX INFO: loaded from: classes8.dex */
public final class EntityTransmuterFactory {
    private final BitVector additions = new BitVector();
    private final BitVector removals = new BitVector();
    private final ComponentTypeFactory types;
    private final World world;

    public EntityTransmuterFactory(World world) {
        this.world = world;
        this.types = world.getComponentManager().typeFactory;
    }

    public EntityTransmuterFactory add(Class<? extends Component> cls) {
        int indexFor = this.types.getIndexFor(cls);
        this.additions.set(indexFor, true);
        this.removals.set(indexFor, false);
        return this;
    }

    public EntityTransmuterFactory remove(Class<? extends Component> cls) {
        int indexFor = this.types.getIndexFor(cls);
        this.additions.set(indexFor, false);
        this.removals.set(indexFor, true);
        return this;
    }

    public EntityTransmuter build() {
        return new EntityTransmuter(this.world, this.additions, this.removals);
    }
}
