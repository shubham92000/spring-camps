package com.bootcamp.springcamp.utils;

public class SkillValue {
    public static Skill getSkill(String skillType){
        return switch (skillType.toLowerCase()) {
            case "beginner" -> Skill.beginner;
            case "intermediate" -> Skill.intermediate;
            case "advanced" -> Skill.advanced;
            default -> null;
        };
    }
}
