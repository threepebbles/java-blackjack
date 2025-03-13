package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BetTest {

    @Test
    void 베팅_금액을_더한다() {
        // given
        final int money1 = 1000;
        final int money2 = 20000;
        Bet bet1 = new Bet(money1);
        Bet bet2 = new Bet(money2);

        // when
        Bet result = bet1.add(bet2);

        // then
        Assertions.assertThat(result).isEqualTo(new Bet(money1 + money2));
    }

    @Test
    void 최소_베팅_금액보다_적게_베팅_금액을_입력한_경우_예외가_발생한다() {
        // given
        final int money = 1000 - 1;

        // when & then
        Assertions.assertThatThrownBy(() -> new Bet(money))
                .isInstanceOf(IllegalArgumentException.class);
    }
}