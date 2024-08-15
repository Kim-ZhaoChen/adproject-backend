package org.example.system.service.impl;

public class DailyActivityCalculator {

    /**
     * 计算每日步数和消耗的卡路里
     *
     * @param steps           每日步数
     * @param sex             性别 (1: 男性, 2: 女性)
     * @param height          身高 (厘米)
     * @param weight          体重 (千克)
     * @param activateMinutes 活动时间 (分钟)
     * @return 行走距离 (公里), 消耗的卡路里 (c)
     */
    public static double[] calculateDailyDistanceCalories(int steps, int sex, double height, double weight, int activateMinutes) {
        // 计算步幅长度（厘米）
        double strideLength = height * (sex == 1 ? 0.415 : 0.413);

        // 计算行走的总距离（公里）
        double distanceKm = (steps * strideLength) / 100000;  // 转换为公里

        // 每分钟步数和每分钟卡路里消耗量
        double stepsPerMinute = (double) steps / activateMinutes;  // 每分钟步数
        double caloriesPerMinute = (weight * 0.035) + (stepsPerMinute * 0.029);  // 估算每分钟的卡路里消耗

        // 计算总卡路里消耗（千卡）
        double totalCaloriesKcal = caloriesPerMinute * activateMinutes;

        // 返回行走距离和消耗的卡路里
        return new double[]{Math.round(distanceKm * 100.0) / 100.0, Math.round(totalCaloriesKcal)};
    }

    public static void main(String[] args) {
        int steps = 9654;
        int sex = 1;
        double height = 170;
        double weight = 70;
        int activateMinutes = 97;

        double[] result = calculateDailyDistanceCalories(steps, sex, height, weight, activateMinutes);
        System.out.printf("行走距离: %.2f 公里, 消耗的卡路里: %d c\n", result[0], (int) result[1]);
    }
}