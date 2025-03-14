package domain;

import domain.bet.BattleResult;
import domain.card.Card;
import domain.card.Deck;
import domain.card.DeckGenerator;
import domain.card.Rank;
import domain.card.Suit;
import domain.player.Dealer;
import domain.player.Players;
import domain.player.User;
import domain.player.Users;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class BlackjackManagerTest {

    @Test
    void 블랙잭_객체를_생성한다() {
        // given
        Dealer dealer = new Dealer();
        Users users = new Users(List.of(
                new User("시소"),
                new User("헤일러"),
                new User("부기"),
                new User("사나")
        ));
        Players players = new Players(dealer, users);

        Deck deck = DeckGenerator.generateDeck();

        // when & then
        Assertions.assertThatCode(() -> new BlackjackManager(players, deck))
                .doesNotThrowAnyException();
    }

    @Test
    void 딜러의_카드_합이_16이하면_카드를_한장_추가한다() {
        // given
        Dealer dealer = new Dealer();
        Users users = new Users(List.of(
                new User("시소"),
                new User("헤일러"),
                new User("부기"),
                new User("사나")
        ));
        Players players = new Players(
                dealer, users
        );

        Deck deck = new Deck(new ArrayList<>(List.of(
                new Card(Suit.SPADE, Rank.EIGHT),
                new Card(Suit.SPADE, Rank.EIGHT),
                new Card(Suit.SPADE, Rank.EIGHT),
                new Card(Suit.SPADE, Rank.EIGHT),
                new Card(Suit.SPADE, Rank.EIGHT),
                new Card(Suit.SPADE, Rank.EIGHT),
                new Card(Suit.SPADE, Rank.EIGHT),
                new Card(Suit.SPADE, Rank.EIGHT),
                new Card(Suit.SPADE, Rank.EIGHT),
                new Card(Suit.SPADE, Rank.EIGHT),

                new Card(Suit.SPADE, Rank.EIGHT)
        )));
        BlackjackManager blackjackManager = new BlackjackManager(players, deck);
        blackjackManager.distributeInitialCards();
        final int beforeSize = dealer.getCards().size();

        // when
        blackjackManager.addCardToDealerIfLowSum();
        final int afterSize = dealer.getCards().size();

        // then
        Assertions.assertThat(afterSize).isEqualTo(beforeSize + 1);
    }

    @Test
    void 참여자들의_승부_결과를_반환한다() {
        // given
        Dealer dealer = new Dealer();

        User siso = new User("시소");
        User heiler = new User("헤일러");
        User boogie = new User("부기");
        User sana = new User("사나");

        Users users = new Users(List.of(siso, heiler, boogie, sana));
        Players players = new Players(dealer, users);

        Deck deck = new Deck(new ArrayList<>(List.of(
                new Card(Suit.SPADE, Rank.FIVE),
                new Card(Suit.SPADE, Rank.FIVE),
                new Card(Suit.SPADE, Rank.THREE),
                new Card(Suit.SPADE, Rank.THREE),
                new Card(Suit.SPADE, Rank.TWO),
                new Card(Suit.SPADE, Rank.TWO),
                new Card(Suit.SPADE, Rank.SEVEN),
                new Card(Suit.SPADE, Rank.SEVEN),
                new Card(Suit.SPADE, Rank.FIVE),
                new Card(Suit.SPADE, Rank.FIVE)
        )));
        BlackjackManager blackjackManager = new BlackjackManager(players, deck);
        blackjackManager.distributeInitialCards();

        // when
        Map<User, BattleResult> usersMatchResult
                = blackjackManager.computeUsersMatchResult();

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(usersMatchResult.get(siso)).isEqualTo(BattleResult.WIN);
            softly.assertThat(usersMatchResult.get(heiler)).isEqualTo(BattleResult.LOSE);
            softly.assertThat(usersMatchResult.get(boogie)).isEqualTo(BattleResult.LOSE);
            softly.assertThat(usersMatchResult.get(sana)).isEqualTo(BattleResult.DRAW);
        });
    }
}
