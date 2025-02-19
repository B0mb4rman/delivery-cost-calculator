package com.example.delivery;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryCostCalculatorTest {

    @Test
    public void testDistanceGreaterThan30() {
        // расстояние > 30 км, габариты большие, груз не хрупкий, загруженность не влияет
        int cost = DeliveryCostCalculator.calculateDeliveryCost(35, "большие", false, "нет загруженности");
        assertEquals(500, cost);
    }

    @Test
    public void testDistanceBetween10And30() {
        // расстояние 20 км, габариты маленькие, груз не хрупкий
        int cost = DeliveryCostCalculator.calculateDeliveryCost(20, "маленькие", false, "нет загруженности");
        assertEquals(400, cost);
    }

    @Test
    public void testDistanceBetween2And10() {
        // расстояние 5 км, габариты большие, груз не хрупкий
        int cost = DeliveryCostCalculator.calculateDeliveryCost(5, "большие", false, "нет загруженности");
        assertEquals(400, cost);
    }

    @Test
    public void testDistanceLessThanOrEqual2() {
        // расстояние 1 км, габариты маленькие, груз не хрупкий
        int cost = DeliveryCostCalculator.calculateDeliveryCost(1, "маленькие", false, "нет загруженности");
        assertEquals(400, cost);
    }

    @Test
    public void testFragileCargoWithinLimit() {
        // груз хрупкий, расстояние 25 км (допустимо), габариты большие
        int cost = DeliveryCostCalculator.calculateDeliveryCost(25, "большие", true, "нет загруженности");
        assertEquals(700, cost);
    }

    @Test
    public void testFragileCargoExceedingDistance() {
        // груз хрупкий, расстояние 35 км
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                DeliveryCostCalculator.calculateDeliveryCost(35, "большие", true, "нет загруженности")
        );
        assertTrue(exception.getMessage().contains("Хрупкий груз не может быть доставлен"));
    }

    @Test
    public void testLoadCoefficientVeryHigh() {
        // расстояние > 30 км, габариты маленькие, не хрупкий, загруженность "очень высокая"
        int cost = DeliveryCostCalculator.calculateDeliveryCost(35, "маленькие", false, "очень высокая загруженность");
        assertEquals(640, cost);
    }

    @Test
    public void testLoadCoefficientHigh() {
        // загруженность "высокая": 400 * 1.4 = 560
        int cost = DeliveryCostCalculator.calculateDeliveryCost(35, "маленькие", false, "высокая загруженность");
        assertEquals(560, cost);
    }

    @Test
    public void testLoadCoefficientIncreased() {
        // загруженность "повышенная": 400 * 1.2 = 480
        int cost = DeliveryCostCalculator.calculateDeliveryCost(35, "маленькие", false, "повышенная загруженность");
        assertEquals(480, cost);
    }

    @Test
    public void testUnknownLoadCoefficient() {
        // незаданная загруженность
        int cost = DeliveryCostCalculator.calculateDeliveryCost(35, "маленькие", false, "нет загруженности");
        assertEquals(400, cost);
    }

    @Test
    public void testMinimumCostEnforcement() {
        // проверка минимальной стоимости: итоговая сумма меньше 400 - должно вернуться 400
        int cost = DeliveryCostCalculator.calculateDeliveryCost(1, "маленькие", false, "нет загруженности");
        assertEquals(400, cost);
    }

    @Test
    public void testUnknownDimensions() {
        // переданы неверные габариты - должно быть выброшено исключение
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                DeliveryCostCalculator.calculateDeliveryCost(10, "средние", false, "нет загруженности")
        );
        assertTrue(exception.getMessage().contains("Неверный размер габаритов"));
    }
}
