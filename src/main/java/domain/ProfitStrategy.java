package domain;

public interface ProfitStrategy {
    Profit calculateProfit(Bet bet, BattleResult battleResult);
}
