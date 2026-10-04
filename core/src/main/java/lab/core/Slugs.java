package lab.core;

import java.util.Locale;

/** Turns titles into URL slugs. */
public final class Slugs {

    private Slugs() {
    }

    public static String of(String title) {
        return title.trim()
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }
}
