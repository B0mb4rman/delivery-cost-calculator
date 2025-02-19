package com.example.delivery;

public class DeliveryCostCalculator {

    public static int calculateDeliveryCost(double distance, String dimensions, boolean fragile, String load) {
        if (fragile && distance > 30) {
            throw new IllegalArgumentException("Хрупкий груз не может быть доставлен на расстояние более 30 км");
        }

        int cost = 0;

        if (distance > 30) {
            cost += 300;
        } else if (distance > 10) { // (10, 30]
            cost += 200;
        } else if (distance > 2) {  // (2, 10]
            cost += 100;
        } else { // [0, 2]
            cost += 50;
        }

        if ("большие".equalsIgnoreCase(dimensions)) {
            cost += 200;
        } else if ("маленькие".equalsIgnoreCase(dimensions)) {
            cost += 100;
        } else {
            throw new IllegalArgumentException("Неверный размер габаритов: " + dimensions);
        }

        if (fragile) {
            cost += 300;
        }

        return getFinalCost(load, cost);
    }

    private static int getFinalCost(String load, int cost) {
        double coefficient = 1.0;
        String loadLower = load.toLowerCase();
        if (loadLower.contains("очень высокая")) {
            coefficient = 1.6;
        } else if (loadLower.contains("высокая")) {
            coefficient = 1.4;
        } else if (loadLower.contains("повышенная")) {
            coefficient = 1.2;
        }

        int finalCost = (int) Math.round(cost * coefficient);

        if (finalCost < 400) {
            finalCost = 400;
        }
        return finalCost;
    }
}
