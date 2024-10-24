package org.tasking.util;

import org.tasking.domain.entities.Tag;

public class TagValidations {
    public static boolean isValidTag(Tag tag) {
        return tag != null
                && tag.getName() != null
                && !tag.getName().trim().isEmpty()
                && tag.getColor() != null
                && tag.getColor().matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
    }
}
