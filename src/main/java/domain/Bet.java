package domain;

import java.util.Objects;

public class Bet {

    public static final int MIN_BET = 1000;

    private final int value;

    public Bet(int value) {
        validateRange(value);
        this.value = value;
    }

    private void validateRange(int value) {
        if (value < MIN_BET) {
            throw new IllegalArgumentException("최소 베팅 금액은 " + MIN_BET + "입니다.");
        }
    }

    public Bet add(Bet willBeAdded) {
        int newValue = this.value + willBeAdded.value;
        validateRange(newValue);
        return new Bet(newValue);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Bet bet)) {
            return false;
        }
        return value == bet.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
