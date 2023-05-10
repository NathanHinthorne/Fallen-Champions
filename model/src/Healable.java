public interface Healable {

    int minHeal = 5;
    int maxHeal = 10;
    double healChance = 50.0;

    public void heal();
}
