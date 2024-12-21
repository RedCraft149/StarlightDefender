package com.redcraft.starlight.shared.entity;

import com.redcraft.starlight.shared.events.EntityAddEvent;
import com.redcraft.starlight.shared.events.EntityPositionChangedEvent;
import com.redcraft.starlight.shared.events.EntityRemoveEvent;
import com.redcraft.starlight.shared.world.Universe;
import com.redcraft.rlib.events.EventListener;
import com.redcraft.rlib.events.Listener;
import com.redcraft.rlib.function.Supplier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityDistributor implements Listener {
    private final Universe universe;
    private final List<DistributionTask> tasks;

    public EntityDistributor(Universe universe) {
        this.universe = universe;
        tasks = new ArrayList<>();
    }

    @EventListener
    public void onEntityMoved(EntityPositionChangedEvent event) {
        DistributionTask task = new DistributionTask().move(event,universe);
        if(task!=null) addTask(task);
    }
    @EventListener
    public void onEntityRemoved(EntityRemoveEvent event) {
        DistributionTask task = new DistributionTask().remove(event.getEntity(),universe);
        addTask(task);
    }
    public void addEntity(Entity entity) {
        DistributionTask task = new DistributionTask().add(entity,universe);
        addTask(task);
        universe.eventHandler().throwEvent(new EntityAddEvent(entity));
    }
    public void removeEntity(Entity entity) {
        DistributionTask task = new DistributionTask().remove(entity,universe);
        addTask(task);
    }
    public void removeEntity(Supplier<Entity> entity, int persistence) {
        DistributionTask task = new DistributionTask().remove(entity,persistence,universe);
        addTask(task);
    }

    private void addTask(DistributionTask task) {
        synchronized (tasks) {
            tasks.add(task);
        }
    }

    public void distribute() {
        synchronized (tasks) {
            Iterator<DistributionTask> itr = tasks.iterator();
            while (itr.hasNext()) {
                DistributionTask task = itr.next();
                if(task.persistence < 0) itr.remove();
                task.persistence--;
                Entity entity = task.entity();
                if(entity == null) {
                    System.err.println("CRITICAL: null entity in distributor! (persistence="+task.persistence+")");
                    continue;
                }

                if (task.remove) universe.getChunk(task.from()).removeEntity(entity);
                if (task.add) universe.getChunk(task.to()).addEntity(entity);
                if (task.remove && !task.add) entity.removeSilent();
                itr.remove();
            }
        }
    }

    private static class DistributionTask {
        boolean remove;
        boolean add;
        int persistence = 0;
        Supplier<Integer> chunkToSupplier;
        Supplier<Integer> chunkFromSupplier;
        Supplier<Entity> entitySupplier;

        public DistributionTask move(EntityPositionChangedEvent event, Universe universe) {
            int chunkTo = universe.getChunkIndex(event.to());
            int chunkFrom = universe.getChunkIndex(event.from());
            if(chunkTo==chunkFrom) return null;
            chunkToSupplier = ()->chunkTo;
            chunkFromSupplier = ()->chunkFrom;

            remove = true;
            add = true;
            entitySupplier = event::getEntity;
            return this;
        }
        public DistributionTask add(Entity entity, Universe universe) {
            int chunkTo = universe.getChunkIndex(entity.position.x,entity.position.y);

            chunkToSupplier = ()->chunkTo;
            chunkFromSupplier = null;

            remove = false;
            add = true;
            this.entitySupplier = ()->entity;
            return this;
        }
        public DistributionTask remove(Entity entity, Universe universe) {
            chunkFromSupplier = ()->universe.getChunkIndex(entity.getPosition());
            chunkToSupplier = null;

            remove = true;
            add = false;
            this.entitySupplier = ()->entity;
            return this;
        }

        public DistributionTask remove(Supplier<Entity> entitySupplier, int persistence, Universe universe) {
            chunkFromSupplier = ()->universe.getChunkIndex(entitySupplier.get().getPosition());
            chunkToSupplier = null;

            remove = true;
            add = false;
            this.persistence = persistence;
            this.entitySupplier = entitySupplier;
            return this;
        }

        public int to() {
            return chunkToSupplier == null ? -1 : chunkToSupplier.get();
        }

        public int from() {
            return chunkFromSupplier == null ? -1 : chunkFromSupplier.get();
        }

        public Entity entity() {
            return entitySupplier.get();
        }
    }
}
