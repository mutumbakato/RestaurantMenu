package com.thinkline256.themenu.ui.dummy;

import com.thinkline256.themenu.utils.Currency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class MenuItemContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyMenuItem> ITEMS = new ArrayList<>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyMenuItem> ITEM_MAP = new HashMap<String, DummyMenuItem>();

    private static final int COUNT = 12;

    static {
        // Add some sample items.
        for (int i = 0; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyMenuItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyMenuItem createDummyItem(int position) {
        return new DummyMenuItem(Currency.format(position * 2000) + "/=", "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about ").append(position);
        for (int i = 1; i < position; i++) {
            builder.append(" More details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyMenuItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyMenuItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
