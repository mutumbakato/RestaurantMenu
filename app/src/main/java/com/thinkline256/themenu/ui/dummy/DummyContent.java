package com.thinkline256.themenu.ui.dummy;

import com.thinkline256.themenu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 4;
    private static String[] titles = {"Break first", "Buffet", "Dinner", "Drinks", "Snacks"};
    private static int[] icons = {R.drawable.coffee, R.drawable.cutlery, R.drawable.restaurant, R.drawable.cocktail, R.drawable.cake};

    static {
        // Add some sample items.
        for (int i = 0; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), titles[position], makeDetails(position), icons[position]);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about ").append(titles[position]);
        for (int i = 0; i < position; i++) {
            builder.append(" More details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;
        public final int icon;

        public DummyItem(String id, String content, String details, int icon) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.icon = icon;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
