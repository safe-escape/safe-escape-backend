package team.safe.escape.population.entity;

public enum PopulationLevel {
    FREE("여유"),

    NORMAL("보통"),

    CROWDED("약간 붐빔"),

    VERY_CROWDED("붐빔");

    private final String level;

    PopulationLevel(String level) {
        this.level = level;
    }

    public static PopulationLevel fromLevel(String level) {
        for (PopulationLevel pl : values()) {
            if (pl.level.equals(level)) {
                return pl;
            }
        }
        throw new IllegalArgumentException("Unknown level: " + level);
    }

}
