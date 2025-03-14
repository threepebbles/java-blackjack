package domain.bet;

public class Profit {
    private int profit;

    public Profit(int profit) {
        this.profit = profit;
    }

    public Profit(Bet bet, BattleResult battleResult) {
        this.profit = (int) (bet.getValue() * battleResult.getWeight());
    }

    public Profit add(int profit) {
        int newProfit = this.profit + profit;
        return new Profit(newProfit);
    }

    public int getProfit() {
        return profit;
    }
}
