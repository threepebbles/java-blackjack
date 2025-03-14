package domain;

import domain.card.Card;
import domain.card.Deck;
import domain.player.Dealer;
import domain.player.Player;
import domain.player.Players;
import domain.player.User;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BlackjackManager {
    private final Players players;
    private final Deck deck;

    public BlackjackManager(Players players, Deck deck) {
        this.players = players;
        this.deck = deck;
    }

    public void distributeInitialCards() {
        players.distributeInitialCards(deck);
    }

    public void openInitialCards() {
        players.openInitialCards();
    }

    public void allUsersHitUntilStay(Function<User, Boolean> wantHit,
                                     BiConsumer<User, List<Card>> callback) {
        for (User user : getUsers()) {
            userHitUntilStay(user, wantHit, callback);
        }
    }

    private void userHitUntilStay(User user,
                                  Function<User, Boolean> wantHit,
                                  BiConsumer<User, List<Card>> callback) {
        while (!user.isBust() && wantHit.apply(user)) {
            user.drawOneCard(deck);
            callback.accept(user, user.getCards());
        }
    }

    public void dealerHitUntilStay(Runnable callback) {
        Dealer dealer = getDealer();
        while (dealer.canHit()) {
            dealer.drawOneCard(deck);
            callback.run();
        }
    }

    public Map<Player, Integer> computePlayerSum() {
        Map<Player, Integer> results = new LinkedHashMap<>();
        for (Player player : players.getPlayers()) {
            results.put(player, player.computeOptimalSum());
        }
        return results;
    }

    public Map<Dealer, Profit> computeDealerProfit() {
        var usersProfit = computeUsersProfit(NormalProfitStrategy.getInstance());
        Profit result = new Profit(usersProfit.values().stream()
                .mapToInt(profit -> -profit.getProfit())
                .sum());
        return Map.of(getDealer(), result);
    }

    public Map<User, Profit> computeUsersProfit(ProfitStrategy profitStrategy) {
        Map<User, BattleResult> usersBattleResult = computeUsersBattleResult();
        return usersBattleResult.entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey,
                        entry -> profitStrategy.calculateProfit(entry.getKey().getBet(), entry.getValue()),
                        (oldValue, newValue) -> newValue,
                        LinkedHashMap::new
                ));
    }

    public Map<User, BattleResult> computeUsersBattleResult() {
        Dealer dealer = getDealer();
        List<User> users = getUsers();

        Map<User, BattleResult> results = new LinkedHashMap<>();
        for (User user : users) {
            results.put(user, BattleResult.fight(dealer, user));
        }
        return results;
    }

    public Dealer getDealer() {
        return players.getDealer();
    }

    public List<User> getUsers() {
        return players.getUsers();
    }
}
