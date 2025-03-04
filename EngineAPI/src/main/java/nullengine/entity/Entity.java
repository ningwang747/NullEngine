package nullengine.entity;

import nullengine.component.GameObject;
import nullengine.logic.Tickable;
import nullengine.world.World;
import org.joml.AABBd;
import org.joml.Vector3d;
import org.joml.Vector3f;

public interface Entity extends GameObject, Tickable {

    EntityProvider getProvider();

    int getId();

    World getWorld();

    // TODO: Don't use Vector3d, it isn't observable
    Vector3d getPosition();

    // TODO: Don't use Vector3f, it isn't observable
    Vector3f getRotation();

    // TODO: Don't use Vector3f, it isn't observable
    Vector3f getMotion();

    AABBd getBoundingBox();

    void destroy();
}
