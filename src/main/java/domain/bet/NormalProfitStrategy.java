package domain.bet;

import java.util.Map;

public class NormalProfitStrategy implements ProfitStrategy {

    private static final double BLACKJACK_WEIGHT = 1.5;
    private static final double NORMAL_WIN_WEIGHT = 1;
    private static final double LOSE_WEIGHT = -1;
    private static final double DRAW_WEIGHT = 0;

    private static final Map<BattleResult, Double> WEIGHT_BY_BATTLE_RESULT = Map.ofEntries(
            Map.entry(BattleResult.BLACKJACK, BLACKJACK_WEIGHT),
            Map.entry(BattleResult.NORMAL_WIN, NORMAL_WIN_WEIGHT),
            Map.entry(BattleResult.LOSE, LOSE_WEIGHT),
            Map.entry(BattleResult.DRAW, DRAW_WEIGHT)
    );

    @Override
    public Profit calculateProfit(Bet bet, BattleResult battleResult) {
        return new Profit((int) (bet.getValue() * WEIGHT_BY_BATTLE_RESULT.get(battleResult)));
    }
}
