package domain.bet;

public interface ProfitStrategy {
    Profit calculateProfit(Bet bet, BattleResult battleResult);
}
