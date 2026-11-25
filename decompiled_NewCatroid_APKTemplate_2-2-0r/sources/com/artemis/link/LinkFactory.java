package com.artemis.link;

import com.artemis.ComponentType;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.EntityId;
import com.artemis.annotations.LinkPolicy;
import com.artemis.utils.Bag;
import com.artemis.utils.IntBag;
import com.artemis.utils.reflect.Annotation;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Field;
import com.artemis.utils.reflect.ReflectionUtil;

/* JADX INFO: loaded from: classes8.dex */
class LinkFactory {
    private static final int MULTI_REFERENCE = 2;
    private static final int NULL_REFERENCE = 0;
    private static final int SINGLE_REFERENCE = 1;
    private final Bag<LinkSite> links = new Bag<>();
    private final ReflexiveMutators reflexiveMutators;
    private final World world;

    public LinkFactory(World world) {
        this.world = world;
        this.reflexiveMutators = new ReflexiveMutators(world);
    }

    static int getReferenceTypeId(Field field) {
        Class type = field.getType();
        if (Entity.class == type) {
            return 1;
        }
        if (ReflectionUtil.isGenericType(field, Bag.class, Entity.class)) {
            return 2;
        }
        boolean z = field.getDeclaredAnnotation(EntityId.class) != null;
        if (Integer.TYPE == type && z) {
            return 1;
        }
        return (IntBag.class == type && z) ? 2 : 0;
    }

    Bag<LinkSite> create(ComponentType componentType) {
        Field[] declaredFields = ClassReflection.getDeclaredFields(componentType.getType());
        this.links.clear();
        for (int i = 0; declaredFields.length > i; i++) {
            Field field = declaredFields[i];
            int referenceTypeId = getReferenceTypeId(field);
            if (referenceTypeId != 0 && LinkPolicy.Policy.SKIP != getPolicy(field)) {
                if (1 == referenceTypeId) {
                    UniLinkSite uniLinkSite = new UniLinkSite(this.world, componentType, field);
                    if (!configureMutator(uniLinkSite)) {
                        this.reflexiveMutators.withMutator(uniLinkSite);
                    }
                    this.links.add(uniLinkSite);
                } else if (2 == referenceTypeId) {
                    MultiLinkSite multiLinkSite = new MultiLinkSite(this.world, componentType, field);
                    if (!configureMutator(multiLinkSite)) {
                        this.reflexiveMutators.withMutator(multiLinkSite);
                    }
                    this.links.add(multiLinkSite);
                }
            }
        }
        return this.links;
    }

    static LinkPolicy.Policy getPolicy(Field field) {
        LinkPolicy linkPolicy;
        Annotation declaredAnnotation = field.getDeclaredAnnotation(LinkPolicy.class);
        if (declaredAnnotation == null || (linkPolicy = (LinkPolicy) declaredAnnotation.getAnnotation(LinkPolicy.class)) == null) {
            return null;
        }
        return linkPolicy.value();
    }

    private boolean configureMutator(UniLinkSite uniLinkSite) {
        UniFieldMutator uniFieldMutator = (UniFieldMutator) MutatorUtil.getGeneratedMutator(uniLinkSite);
        if (uniFieldMutator == null) {
            return false;
        }
        uniFieldMutator.setWorld(this.world);
        uniLinkSite.fieldMutator = uniFieldMutator;
        return true;
    }

    private boolean configureMutator(MultiLinkSite multiLinkSite) {
        MultiFieldMutator multiFieldMutator = (MultiFieldMutator) MutatorUtil.getGeneratedMutator(multiLinkSite);
        if (multiFieldMutator == null) {
            return false;
        }
        multiFieldMutator.setWorld(this.world);
        multiLinkSite.fieldMutator = multiFieldMutator;
        return true;
    }

    static class ReflexiveMutators {
        final EntityBagFieldMutator entityBagField;
        final EntityFieldMutator entityField;
        final IntBagFieldMutator intBagField;
        final IntFieldMutator intField;

        public ReflexiveMutators(World world) {
            EntityFieldMutator entityFieldMutator = new EntityFieldMutator();
            this.entityField = entityFieldMutator;
            entityFieldMutator.setWorld(world);
            IntFieldMutator intFieldMutator = new IntFieldMutator();
            this.intField = intFieldMutator;
            intFieldMutator.setWorld(world);
            IntBagFieldMutator intBagFieldMutator = new IntBagFieldMutator();
            this.intBagField = intBagFieldMutator;
            intBagFieldMutator.setWorld(world);
            EntityBagFieldMutator entityBagFieldMutator = new EntityBagFieldMutator();
            this.entityBagField = entityBagFieldMutator;
            entityBagFieldMutator.setWorld(world);
        }

        UniLinkSite withMutator(UniLinkSite uniLinkSite) {
            if (uniLinkSite.fieldMutator != null) {
                return uniLinkSite;
            }
            Class type = uniLinkSite.field.getType();
            if (Entity.class == type) {
                uniLinkSite.fieldMutator = this.entityField;
            } else if (Integer.TYPE == type) {
                uniLinkSite.fieldMutator = this.intField;
            } else {
                throw new RuntimeException("unexpected '" + type + "', on " + uniLinkSite.type);
            }
            return uniLinkSite;
        }

        MultiLinkSite withMutator(MultiLinkSite multiLinkSite) {
            if (multiLinkSite.fieldMutator != null) {
                return multiLinkSite;
            }
            Class type = multiLinkSite.field.getType();
            if (IntBag.class == type) {
                multiLinkSite.fieldMutator = this.intBagField;
            } else if (Bag.class == type) {
                multiLinkSite.fieldMutator = this.entityBagField;
            } else {
                throw new RuntimeException("unexpected '" + type + "', on " + multiLinkSite.type);
            }
            return multiLinkSite;
        }
    }
}
