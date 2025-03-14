package domain.bet;

import domain.player.Dealer;
import domain.player.User;

public enum BattleResult {
    BLACKJACK(1.5),
    WIN(1),
    LOSE(-1),
    DRAW(0),
    ;

    private final double weight;

    BattleResult(double weight) {
        this.weight = weight;
    }

    public static BattleResult fight(Dealer dealer, User user) {
        if (user.isBust()) {
            return BattleResult.LOSE;
        }
        if (dealer.isBust()) {
            return BattleResult.WIN;
        }
        if (user.isBlackjack() && dealer.isBlackjack()) {
            return BattleResult.DRAW;
        }
        if (user.isBlackjack()) {
            return BattleResult.BLACKJACK;
        }
        return compareBySum(user.computeOptimalSum(), dealer.computeOptimalSum());
    }

    private static BattleResult compareBySum(int sum1, int sum2) {
        if (sum1 > sum2) {
            return WIN;
        }
        if (sum1 < sum2) {
            return LOSE;
        }
        return DRAW;
    }

    public double getWeight() {
        return weight;
    }
}
