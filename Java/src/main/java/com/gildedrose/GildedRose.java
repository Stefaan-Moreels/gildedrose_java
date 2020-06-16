package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        // because the factory is static inner class, method can not be static
        CategorizedItemFactory factory = new CategorizedItemFactory();
        for (Item item : items) {
            factory.itemToCategorizedItem(item).update();
        }
    }

    public static class CategorizedItem {

        Item item ;

        // getter
        public Item getItem() {
            return item;
        }
        // setter
        public void setItem(Item item) {
            this.item = item;
        }

        // Constructor
        public CategorizedItem(Item item) {
            this.item = item;
        }

        public void update() {
            updateQuality();
            updateSellIn();
            if (getItem().sellIn < 0) {
                updateNotSold();
            }
        }

        // default update methods
        protected void updateNotSold() {
            decrementQuality();
        }
        protected void updateSellIn() {
            decrementSellIn();
        }
        protected void updateQuality() {
            decrementQuality();
        }

        // increment
        protected void incrementSellIn() {
            item.sellIn = item.sellIn + 1;
        }
        // decrement
        protected void decrementSellIn() {
            item.sellIn = item.sellIn - 1;
        }


        // increment
        protected void incrementQuality() {
            incrementQuality(1);
        }
        protected void incrementQuality(int number) {
            if (item.quality <= 50 - number) {
                item.quality = item.quality + number;
            } else {
                item.quality = 50;
            }
        }

        // decrement
        protected void decrementQuality() {
            decrementQuality(1);
        }
        protected void decrementQuality(int number) {
            if (item.quality >= number) {
                item.quality = item.quality - number;
            } else {
                item.quality = 0;
            }
        }

    }

    public static class AgedBrieItem extends CategorizedItem {

        public AgedBrieItem(Item item) {
            super(item);
        }

        @Override
        protected void updateNotSold() {
            incrementQuality();
        }

        @Override
        protected void updateQuality() {
            incrementQuality();
        }

    }

    public static class LegendaryItem extends CategorizedItem {

        public LegendaryItem(Item item) {
            super(item);
        }

        @Override
        protected void incrementQuality() {
            throw new InMutableException();
        }

        @Override
        protected void incrementQuality(int number) {
            throw new InMutableException();
        }

        @Override
        protected void decrementQuality() {
            throw new InMutableException();
        }

        @Override
        protected void decrementQuality(int number) {
            throw new InMutableException();
        }

        @Override
        protected void updateQuality() {
            // left blank
        }

        @Override
        protected void updateSellIn() {
            // left blank
        }

        @Override
        protected void updateNotSold() {
            // left blank
        }
    }

    public static class BackstagePassItem extends CategorizedItem {

        public BackstagePassItem(Item item) {
            super(item);
        }

        @Override
        protected void updateNotSold() {
            item.quality = 0;
        }

        @Override
        protected void updateQuality() {
            incrementQuality();
            if (item.sellIn <= 10) {
                incrementQuality();
            }
            if (item.sellIn <= 5) {
                incrementQuality();
            }
        }

    }

    public static class ConjuredItem extends CategorizedItem {

        public ConjuredItem(Item item) {
            super(item);
        }

        @Override
        protected void updateQuality() {
            decrementQuality(2);
        }

        @Override
        protected void updateNotSold() {
            decrementQuality(2);
        }
    }

    public static class CategorizedItemFactory {
        public CategorizedItem itemToCategorizedItem(Item item) {
            switch (item.name) {
                case "Aged Brie":
                    return new AgedBrieItem(item);
                case "Sulfuras, Hand of Ragnaros":
                    return new LegendaryItem(item);
                case "Backstage passes to a TAFKAL80ETC concert":
                    return new BackstagePassItem(item);
                case "Conjured Mana Cake":
                    return new ConjuredItem(item);
                default:
                    return new CategorizedItem(item);
            }
        }
    }

    public static class InMutableException extends RuntimeException {
        public InMutableException() {
            super("Inmutable property");
        }
    }
}