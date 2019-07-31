package nl.bos.models;

import javafx.scene.image.Image;

public class Exercise {
    private final long id;
    private final String name;
    private final String description;
    private final Image icon;

    public Exercise(long id, String name, String description, Image icon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Image getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }
}
