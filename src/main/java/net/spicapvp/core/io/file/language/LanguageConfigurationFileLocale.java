package net.spicapvp.core.io.file.language;

public enum LanguageConfigurationFileLocale
{
    ENGLISH("en"),
    EXPLICIT("ex"),
    FRENCH("fr"),
    SPANISH("es"),
    PORTUGUESE("pt");

    public String getAbbreviation() { return this.abbreviation; }

    private final String abbreviation;
    private LanguageConfigurationFileLocale(String abbreviation) { this.abbreviation = abbreviation; }


    public static LanguageConfigurationFileLocale getByAbbreviation(String abbreviation) {
        for (LanguageConfigurationFileLocale locale : values()) {
            if (locale.getAbbreviation().equalsIgnoreCase(abbreviation)) {
                return locale;
            }
        }
        return ENGLISH;
    }

    public static LanguageConfigurationFileLocale getByName(String name) {
        for (LanguageConfigurationFileLocale locale : values()) {
            if (locale.name().equalsIgnoreCase(name)) {
                return locale;
            }
        }
        return ENGLISH;
    }
}
