package view;

import domain.card.Card;
import domain.player.Dealer;
import domain.player.Player;
import domain.player.User;
import domain.stats.MatchResult;
import java.util.List;
import java.util.Map;

public class OutputView {
    public static void printInitialCards(Dealer dealer, List<User> users) {
        List<String> names = users.stream()
                .map(Player::getName)
                .toList();

        System.out.printf("%s와 %s에게 2장을 나누었습니다.%n", dealer.getName(), String.join(", ", names));

        printPlayerCards(dealer, dealer.getCards());
        users.forEach(user -> printPlayerCards(user, user.getCards()));
        System.out.println();
    }

    public static void printPlayerCards(Player player, List<Card> cards) {
        System.out.printf("%s카드: %s%n", player.getName(),
                String.join(", ", cards.stream()
                        .map(card -> String.format("%s%s", card.getRank().getTitle(),
                                card.getSuit().getTitle()))
                        .toList()));
    }

    public static void printPlayersCardsAndSum(Dealer dealer,
                                               List<User> users,
                                               Map<Player, Integer> playerSum) {
        printPlayerCardsAndSum(dealer, findNameAndSumByName(dealer, playerSum));
        users.forEach(player -> printPlayerCardsAndSum(player, findNameAndSumByName(player, playerSum)));
        System.out.println();
    }

    private static int findNameAndSumByName(Player player, Map<Player, Integer> playerSum) {
        return playerSum.get(player);
    }

    private static void printPlayerCardsAndSum(Player player, int sum) {
        System.out.printf("%s카드: %s - 결과: %d%n", player.getName(),
                String.join(", ",
                        player.getCards().stream()
                                .map(card -> String.format("%s%s", card.getRank().getTitle(),
                                        card.getSuit().getTitle()))
                                .toList()),
                sum);
    }

    public static void printAddCardToDealer() {
        System.out.printf("딜러는 16이하라 한장의 카드를 더 받았습니다.%n%n");
    }

    public static void printMatchResults(Dealer dealer, Map<MatchResult, Integer> dealerResult,
                                         Map<Player, MatchResult> usersMathResult) {
        System.out.printf("%s: %s%n", dealer.getName(), convertToDealerMatchResult(dealerResult));
        usersMathResult.forEach((key, value) -> System.out.printf("%s: %s%n", key, value.getTitle()));
    }

    private static String convertToDealerMatchResult(Map<MatchResult, Integer> dealerResult) {
        StringBuilder sb = new StringBuilder();
        dealerResult.forEach((key, value) ->
                sb.append(String.format("%d%s ", value, key.getTitle()))
        );
        return sb.toString();
    }
}
