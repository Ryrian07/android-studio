package com.example.summer.utils;

import android.util.Log;
import com.example.summer.datas.SpotData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 景点推荐器，用于根据用户年龄、性别以及搜索历史推荐景点
 */
public class SpotRecommender {
    private SpotData spotData;
    private String gender;
    private int age;
    
    // 景点简介信息
    private static final Map<String, String> spotDescriptions = new HashMap<>();
    
    static {
        // 自然风光类
        spotDescriptions.put("如意湖", "如意湖是避暑山庄的主要人工湖泊之一，水面开阔，湖畔风景优美，是游船观景和拍照的理想地点。");
        spotDescriptions.put("上湖", "上湖位于避暑山庄东北部，湖面开阔，环境清幽，是理想的赏景休憩之处。");
        spotDescriptions.put("热河泉", "热河泉是避暑山庄著名的温泉之一，四季不竭，水温恒定，具有一定的医疗保健功效。");
        spotDescriptions.put("内湖", "内湖是避暑山庄的核心湖区，环绕于宫殿建筑周围，湖水清澈，景色宜人。");
        spotDescriptions.put("南山积雪", "南山积雪是避暑山庄著名的\"三十六景\"之一，远眺雪山，景色壮观。");
        spotDescriptions.put("四面云山", "四面云山是避暑山庄的自然景观，山峦环绕，云雾缭绕，景色秀美。");
        spotDescriptions.put("万树园", "万树园是避暑山庄内的一处大型植物园，园内林木葱郁，花草繁茂，是亲近自然的好去处。");
        spotDescriptions.put("甫田丛樾", "甫田丛樾是一处模仿江南田园风光的区域，田园风光与宫廷园林相结合，景色优美。");
        
        // 历史文化类
        spotDescriptions.put("溥仁寺", "溥仁寺是避暑山庄外八庙之一，建筑风格独特，融合了汉、藏、蒙古等多种建筑风格。");
        spotDescriptions.put("普宁寺", "普宁寺是避暑山庄外八庙中规模最大的寺庙，内有世界上最大的木制佛像之一。");
        spotDescriptions.put("法林寺", "法林寺是避暑山庄外八庙之一，寺内供奉释迦牟尼佛，建筑风格独特。");
        spotDescriptions.put("碧峰寺遗址", "碧峰寺遗址保存了清代寺庙建筑的重要历史遗迹，具有重要的考古价值。");
        spotDescriptions.put("灵泽龙王庙", "灵泽龙王庙供奉龙王，是祈雨祭祀的场所，建筑形式独特，装饰精美。");
        spotDescriptions.put("永佑寺", "永佑寺是避暑山庄外八庙之一，寺内壁画和雕塑艺术精湛，是研究清代艺术的重要场所。");
        spotDescriptions.put("曲水荷香", "曲水荷香是避暑山庄的著名景点，水道蜿蜒，荷花盛开，景色秀美。");
        spotDescriptions.put("濠濮间想", "濠濮间想是避暑山庄的一处园林景观，取自《庄子·秋水》典故，环境优雅。");
        spotDescriptions.put("避暑山庄碑", "避暑山庄碑是清代皇家文化的重要遗存，碑文由康熙皇帝亲自撰写，具有重要的历史文化价值。");
        spotDescriptions.put("双湖夹镜碑", "双湖夹镜碑是避暑山庄的景观碑刻，镜湖倒影，风景如画。");
        spotDescriptions.put("绿毯八韵碑", "绿毯八韵碑是避暑山庄的文化景观，碑上刻有描写避暑山庄自然景色的诗词。");
        spotDescriptions.put("澹泊敬诚殿", "澹泊敬诚殿是避暑山庄的主要宫殿建筑之一，是清代皇帝处理政务的地方，建筑风格典雅庄重。");
        spotDescriptions.put("四知书屋", "四知书屋是避暑山庄的一处静谧书房，是清代皇帝读书学习的地方，环境幽静。");
        spotDescriptions.put("烟波致爽殿", "烟波致爽殿是避暑山庄的主要宫殿之一，位于湖畔，可以欣赏湖光山色，是休闲赏景的好去处。");
        
        // 景区设施类
        spotDescriptions.put("公共厕所1", "位于避暑山庄丽正门附近的公共卫生间，设施齐全，干净卫生。");
        spotDescriptions.put("公共厕所2", "位于避暑山庄湖区附近的公共卫生间，方便游客使用。");
        spotDescriptions.put("公共厕所3", "位于避暑山庄宫殿区附近的公共卫生间，环境整洁。");
        spotDescriptions.put("公共厕所4", "位于避暑山庄山区附近的公共卫生间，配备无障碍设施。");
        spotDescriptions.put("地下停车场", "避暑山庄地下停车场，可容纳大量车辆，设施完善，交通便利。");
        spotDescriptions.put("地上停车场", "避暑山庄地上停车场，靠近景区入口，方便快捷。");
        spotDescriptions.put("丽正门", "避暑山庄的正门，是进入景区的主要入口，建筑宏伟，装饰精美。");
        spotDescriptions.put("德汇门", "避暑山庄的侧门，人流量较少，适合快速进出景区。");
        spotDescriptions.put("游客中心", "避暑山庄游客中心提供导游服务、地图、语音讲解器租赁等服务，是游客获取信息的主要场所。");
    }
    
    // 景点分类映射
    private static final Map<String, String> spotCategories = new HashMap<>();
    
    static {
        // 自然风光类
        spotCategories.put("如意湖", "自然风光");
        spotCategories.put("上湖", "自然风光");
        spotCategories.put("热河泉", "自然风光");
        spotCategories.put("内湖", "自然风光");
        spotCategories.put("南山积雪", "自然风光");
        spotCategories.put("四面云山", "自然风光");
        spotCategories.put("万树园", "自然风光");
        spotCategories.put("甫田丛樾", "自然风光");
        
        // 历史文化类
        spotCategories.put("溥仁寺", "历史文化");
        spotCategories.put("普宁寺", "历史文化");
        spotCategories.put("法林寺", "历史文化");
        spotCategories.put("碧峰寺遗址", "历史文化");
        spotCategories.put("灵泽龙王庙", "历史文化");
        spotCategories.put("永佑寺", "历史文化");
        spotCategories.put("曲水荷香", "历史文化");
        spotCategories.put("濠濮间想", "历史文化");
        spotCategories.put("避暑山庄碑", "历史文化");
        spotCategories.put("双湖夹镜碑", "历史文化");
        spotCategories.put("绿毯八韵碑", "历史文化");
        spotCategories.put("澹泊敬诚殿", "历史文化");
        spotCategories.put("四知书屋", "历史文化");
        spotCategories.put("烟波致爽殿", "历史文化");
        
        // 景区设施类
        spotCategories.put("公共厕所1", "景区设施");
        spotCategories.put("公共厕所2", "景区设施");
        spotCategories.put("公共厕所3", "景区设施");
        spotCategories.put("公共厕所4", "景区设施");
        spotCategories.put("地下停车场", "景区设施");
        spotCategories.put("地上停车场", "景区设施");
        spotCategories.put("丽正门", "景区设施");
        spotCategories.put("德汇门", "景区设施");
        spotCategories.put("游客中心", "景区设施");
    }
    
    /**
     * 构造函数
     * @param spotData 景点数据实例
     * @param gender 用户性别（"男"或"女"）
     * @param age 用户年龄
     */
    public SpotRecommender(SpotData spotData, String gender, int age) {
        this.spotData = spotData;
        this.gender = gender;
        this.age = age;
    }
    
    /**
     * 根据用户信息和搜索历史推荐景点
     * @return 推荐景点列表（最多3个）
     */
    public List<Map<String, String>> recommendSpots() {
        List<Map<String, String>> recommendedSpots = new ArrayList<>();
        
        // 获取所有景点
        List<String> allSpots = getAllSpots();
        
        // 计算每个景点的推荐分数
        Map<String, Double> spotScores = calculateSpotScores(allSpots);
        
        // 按分数排序
        List<Map.Entry<String, Double>> sortedSpots = new ArrayList<>(spotScores.entrySet());
        Collections.sort(sortedSpots, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        
        // 取前三个作为推荐
        int count = 0;
        for (Map.Entry<String, Double> entry : sortedSpots) {
            if (count >= 3) break;
            
            String spotName = entry.getKey();
            String description = spotDescriptions.getOrDefault(spotName, "这是一个美丽的景点，值得一游。");
            String category = spotCategories.getOrDefault(spotName, "");
            
            Map<String, String> spotInfo = new HashMap<>();
            spotInfo.put("name", spotName);
            spotInfo.put("description", description);
            spotInfo.put("category", category);
            
            recommendedSpots.add(spotInfo);
            count++;
        }
        
        return recommendedSpots;
    }
    
    /**
     * 根据用户搜索历史找出最常搜索的景点类别
     * @return 最常搜索的景点类别
     */
    public String getMostSearchedCategory() {
        Map<String, Integer> categoryCounts = new HashMap<>();
        categoryCounts.put("自然风光", 0);
        categoryCounts.put("历史文化", 0);
        categoryCounts.put("景区设施", 0);
        
        // 获取所有景点
        List<String> allSpots = getAllSpots();
        
        // 统计各类别的搜索次数
        for (String spot : allSpots) {
            // 不带人流量信息的景点名
            String spotName = spot;
            if (spot.contains("（")) {
                spotName = spot.substring(0, spot.indexOf("（"));
            }
            
            String category = spotCategories.getOrDefault(spotName, "");
            if (!category.isEmpty()) {
                try {
                    // 获取该景点的搜索次数
                    int searchTimes = getSpotSearchTimes(spotName);
                    
                    // 累加到对应类别
                    if (categoryCounts.containsKey(category)) {
                        categoryCounts.put(category, categoryCounts.get(category) + searchTimes);
                    }
                } catch (Exception e) {
                    Log.e("SpotRecommender", "Error getting search times for spot: " + spotName, e);
                }
            }
        }
        
        // 找出搜索次数最多的类别
        String mostSearchedCategory = "历史文化"; // 默认类别
        int maxCount = 0;
        
        for (Map.Entry<String, Integer> entry : categoryCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostSearchedCategory = entry.getKey();
            }
        }
        
        return mostSearchedCategory;
    }
    
    /**
     * 根据类别获取相关景点列表
     * @param category 景点类别
     * @return 该类别下的景点列表
     */
    public List<Map<String, String>> getSpotsByCategory(String category) {
        List<Map<String, String>> categorySpots = new ArrayList<>();
        
        // 获取所有景点
        List<String> allSpots = getAllSpots();
        
        // 筛选出指定类别的景点
        for (String spot : allSpots) {
            // 不带人流量信息的景点名
            String spotName = spot;
            if (spot.contains("（")) {
                spotName = spot.substring(0, spot.indexOf("（"));
            }
            
            String spotCategory = spotCategories.getOrDefault(spotName, "");
            if (spotCategory.equals(category)) {
                String description = spotDescriptions.getOrDefault(spotName, "这是一个美丽的景点，值得一游。");
                
                Map<String, String> spotInfo = new HashMap<>();
                spotInfo.put("name", spotName);
                spotInfo.put("description", description);
                spotInfo.put("category", category);
                
                categorySpots.add(spotInfo);
            }
        }
        
        return categorySpots;
    }
    
    /**
     * 计算景点推荐分数
     * @param spots 景点列表
     * @return 景点分数映射
     */
    private Map<String, Double> calculateSpotScores(List<String> spots) {
        Map<String, Double> scores = new HashMap<>();
        
        for (String spot : spots) {
            // 不带人流量信息的景点名
            String spotName = spot;
            if (spot.contains("（")) {
                spotName = spot.substring(0, spot.indexOf("（"));
            }
            
            // 基础分数（根据搜索次数）
            double score = getSpotSearchTimes(spotName) * 10.0;
            
            // 根据年龄调整分数
            score += calculateAgeScore(spotName);
            
            // 根据性别调整分数
            score += calculateGenderScore(spotName);
            
            // 随机因素（±10%）
            double randomFactor = 0.9 + Math.random() * 0.2;
            score *= randomFactor;
            
            scores.put(spotName, score);
        }
        
        return scores;
    }
    
    /**
     * 根据年龄计算额外推荐分数
     * @param spotName 景点名称
     * @return 年龄相关的额外分数
     */
    private double calculateAgeScore(String spotName) {
        String category = spotCategories.getOrDefault(spotName, "");
        
        // 根据年龄段和景点类别分配加权
        if (age < 18) {
            // 年轻人更喜欢自然风光
            if (category.equals("自然风光")) return 30.0;
            if (category.equals("历史文化")) return 10.0;
            if (category.equals("景区设施")) return 20.0;
        } else if (age < 40) {
            // 青年人平衡倾向
            if (category.equals("自然风光")) return 20.0;
            if (category.equals("历史文化")) return 25.0;
            if (category.equals("景区设施")) return 15.0;
        } else if (age < 60) {
            // 中年人更喜欢历史文化
            if (category.equals("自然风光")) return 15.0;
            if (category.equals("历史文化")) return 35.0;
            if (category.equals("景区设施")) return 15.0;
        } else {
            // 老年人更喜欢平缓、方便的景点
            if (category.equals("自然风光")) return 10.0;
            if (category.equals("历史文化")) return 30.0;
            if (category.equals("景区设施")) return 25.0;
        }
        
        return 0.0;
    }
    
    /**
     * 根据性别计算额外推荐分数
     * @param spotName 景点名称
     * @return 性别相关的额外分数
     */
    private double calculateGenderScore(String spotName) {
        String category = spotCategories.getOrDefault(spotName, "");
        
        // 根据性别偏好分配加权
        if ("男".equals(gender)) {
            if (category.equals("自然风光")) return 20.0;
            if (category.equals("历史文化")) return 25.0;
            if (category.equals("景区设施")) return 15.0;
        } else if ("女".equals(gender)) {
            if (category.equals("自然风光")) return 25.0;
            if (category.equals("历史文化")) return 20.0;
            if (category.equals("景区设施")) return 15.0;
        }
        
        return 0.0;
    }
    
    /**
     * 获取景点的搜索次数
     * @param spotName 景点名称
     * @return 搜索次数
     */
    private int getSpotSearchTimes(String spotName) {
        try {
            // 这里应该通过SpotData中的方法获取搜索次数
            int crowd = spotData.getCrowdByLocationName(spotName);
            
            // 防止负值
            return Math.max(0, crowd % 50 + 1);
        } catch (Exception e) {
            Log.e("SpotRecommender", "Error getting search times", e);
            return 1; // 默认值
        }
    }
    
    /**
     * 获取所有景点列表
     * @return 景点列表
     */
    private List<String> getAllSpots() {
        List<String> allSpots = new ArrayList<>();
        
        // 从各个类别获取景点
        try {
            allSpots.addAll(spotData.optimizedSearch("自然风光"));
            allSpots.addAll(spotData.optimizedSearch("历史文化"));
            allSpots.addAll(spotData.optimizedSearch("景区设施"));
        } catch (Exception e) {
            Log.e("SpotRecommender", "Error getting spots", e);
        }
        
        return allSpots;
    }
} 