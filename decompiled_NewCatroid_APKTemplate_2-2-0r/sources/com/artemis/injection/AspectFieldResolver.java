package com.artemis.injection;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.EntitySubscription;
import com.artemis.EntityTransmuter;
import com.artemis.World;
import com.artemis.annotations.All;
import com.artemis.annotations.AspectDescriptor;
import com.artemis.annotations.Exclude;
import com.artemis.annotations.One;
import com.artemis.utils.reflect.Annotation;
import com.artemis.utils.reflect.Field;
import java.util.IdentityHashMap;

/* JADX INFO: loaded from: classes8.dex */
public class AspectFieldResolver implements FieldResolver {
    private static final Class<? extends Component>[] EMPTY_COMPONENT_CLASS_ARRAY = new Class[0];
    private IdentityHashMap<Field, Aspect.Builder> fields = new IdentityHashMap<>();
    private World world;

    @Override // com.artemis.injection.FieldResolver
    public void initialize(World world) {
        this.world = world;
    }

    @Override // com.artemis.injection.FieldResolver
    public Object resolve(Object obj, Class<?> cls, Field field) {
        Aspect.Builder builderAspect = aspect(field);
        if (builderAspect == null) {
            return null;
        }
        if (Aspect.class == cls) {
            return this.world.getAspectSubscriptionManager().get(builderAspect).getAspect();
        }
        if (Aspect.Builder.class == cls) {
            return builderAspect;
        }
        if (EntityTransmuter.class == cls) {
            return new EntityTransmuter(this.world, builderAspect);
        }
        if (EntitySubscription.class == cls) {
            return this.world.getAspectSubscriptionManager().get(builderAspect);
        }
        if (Archetype.class == cls) {
            return new ArchetypeBuilder().add(allComponents(field)).build(this.world);
        }
        return null;
    }

    private Aspect.Builder aspect(Field field) {
        if (!this.fields.containsKey(field)) {
            AspectDescriptor aspectDescriptorDescriptor = descriptor(field);
            if (aspectDescriptorDescriptor != null) {
                this.fields.put(field, toAspect(aspectDescriptorDescriptor));
            } else {
                All all = (All) field.getAnnotation(All.class);
                One one = (One) field.getAnnotation(One.class);
                Exclude exclude = (Exclude) field.getAnnotation(Exclude.class);
                if (all != null || one != null || exclude != null) {
                    this.fields.put(field, toAspect(all, one, exclude));
                } else {
                    this.fields.put(field, null);
                }
            }
        }
        return this.fields.get(field);
    }

    private AspectDescriptor descriptor(Field field) {
        Annotation declaredAnnotation = field.getDeclaredAnnotation(AspectDescriptor.class);
        if (declaredAnnotation != null) {
            return (AspectDescriptor) declaredAnnotation.getAnnotation(AspectDescriptor.class);
        }
        return null;
    }

    private Aspect.Builder toAspect(AspectDescriptor aspectDescriptor) {
        return Aspect.all(aspectDescriptor.all()).one(aspectDescriptor.one()).exclude(aspectDescriptor.exclude());
    }

    private Aspect.Builder toAspect(All all, One one, Exclude exclude) {
        return Aspect.all(all != null ? all.value() : EMPTY_COMPONENT_CLASS_ARRAY).one(one != null ? one.value() : EMPTY_COMPONENT_CLASS_ARRAY).exclude(exclude != null ? exclude.value() : EMPTY_COMPONENT_CLASS_ARRAY);
    }

    private Class<? extends Component>[] allComponents(Field field) {
        AspectDescriptor aspectDescriptorDescriptor = descriptor(field);
        if (aspectDescriptorDescriptor != null) {
            return aspectDescriptorDescriptor.all();
        }
        All all = (All) field.getAnnotation(All.class);
        if (all != null) {
            return all.value();
        }
        return null;
    }
}
