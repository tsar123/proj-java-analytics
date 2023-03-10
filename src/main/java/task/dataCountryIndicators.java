package task;

//Разработать набор классов с необходимым составом полей и методов для хранения
//и обработки всех данных, хранящихся в CSV-файле вашего варианта задания.
public class dataCountryIndicators {
    //Country,Region,Happiness Rank,Happiness Score,Standard Error,Economy (GDP per Capital),Family,Health (Life Expectancy),Freedom,Trust (Government Corruption),Generosity,Dystopia Residual
    public final String country;
    public final String region;
    public final int happyRank;
    public final float happyScore;
    public final float standardError;
    public final float economy;
    public final float family;
    public final float health;
    public final float freedom;
    public final float trust;
    public final float generosity;
    public final float dystopiaResidual;

    public float getEconomy() {
        return economy;
    }

    dataCountryIndicators(String country, String region, int happyRank, float happyScore, float standardError, float economy, float family, float health, float freedom, float trust, float generosity, float dystopiaResidual) {
        this.country = country;
        this.region = region;
        this.happyRank = happyRank;
        this.happyScore = happyScore;
        this.standardError = standardError;
        this.economy = economy;
        this.family = family;
        this.health = health;
        this.freedom = freedom;
        this.trust = trust;
        this.generosity = generosity;
        this.dystopiaResidual = dystopiaResidual;
    }

    dataCountryIndicators(String[] str) {
        this.country = str[0];
        this.region = str[1];
        this.happyRank = Integer.parseInt(str[2]);
        this.happyScore = Float.parseFloat(str[3]);
        this.standardError = Float.parseFloat(str[4]);
        this.economy = Float.parseFloat(str[5]);
        this.family = Float.parseFloat(str[6]);
        this.health = Float.parseFloat(str[7]);
        this.freedom = Float.parseFloat(str[8]);
        this.trust = Float.parseFloat(str[9]);
        this.generosity = Float.parseFloat(str[10]);
        this.dystopiaResidual = Float.parseFloat(str[11]);
    }

    @Override
    public String toString() {
        return String.format("Страна: %s | Регион: %s | Ранг(счастье): %d | Очки счастья: %.3f | Стандартная ошибка: %.5f | Экономика: %.5f | Семья: %.5f | Здоровье: %.5f | Свобода: %.5f | Доверие: %.5f | Щедрость: %.5f | Остаточная антиутопия: %.5f",
                this.country, this.region, this.happyRank, this.happyScore, this.standardError, this.economy, this.family, this.health, this.freedom, this.trust, this.generosity, this.dystopiaResidual);
    }
}
