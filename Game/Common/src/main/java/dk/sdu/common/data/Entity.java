package dk.sdu.common.data;

import dk.sdu.common.data.entityparts.EntityPart;
import dk.sdu.common.services.IComponentProcessingService;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();
    private HashMap<Class, IComponentProcessingService> components;

    private float[] shapeX = new float[4];
    private float[] shapeY = new float[4];
    private float radius;
    private float[] colour;
    private Map<Class, EntityPart> parts;
    
    private String image;

    public Entity() {
        parts = new ConcurrentHashMap<>();
        this.components = new HashMap<>();
    }
    
    public Entity(String image) {
        this();
        this.image = image;
    }

    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }

    public void remove(Class partClass) {
        parts.remove(partClass);
    }

    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }

    public void setRadius(float r) {
        this.radius = r;
    }

    public float getRadius() {
        return radius;
    }

    public String getID() {
        return ID.toString();
    }

    public float[] getShapeX() {
        return shapeX;
    }
    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setShapeX(float[] shapeX) {
        this.shapeX = shapeX;
    }

    public float[] getShapeY() {
        return shapeY;
    }

    public void setShapeY(float[] shapeY) {
        this.shapeY = shapeY;
    }

    public float[] getColour() {
        return this.colour;
    }

    public void setColour(float[] c) {
        this.colour = c;
    }
    
    public void addComponent(IComponentProcessingService component) {
        this.components.put(component.getClass(), component);
    }

    public void removeComponent(Class componentClass) {
        if (this.components.containsKey(componentClass)) {
            this.components.remove(componentClass);
        }
    }

    public <O extends IComponentProcessingService> O getComponent(Class componentClass) {
        return (O) this.components.get(componentClass);
    }

    public boolean hasComponent(Class componentClass) {
        return this.components.containsKey(componentClass);
    }
}
