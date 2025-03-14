package domain;

import domain.bet.Bet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BetTest {

    @Test
    void 최소_베팅_금액보다_적게_베팅_금액을_입력한_경우_예외가_발생한다() {
        // given
        final int money = 1000 - 1;

        // when & then
        Assertions.assertThatThrownBy(() -> new Bet(money))
                .isInstanceOf(IllegalArgumentException.class);
    }
}