package view;

import domain.player.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);

    public static List<String> inputUserName() {
        System.out.println("게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)");
        String names = scanner.nextLine();
        return Arrays.stream(names.split(",", -1))
                .map(String::strip)
                .toList();
    }

    public static boolean inputWantOneMoreCard(Player player) {
        System.out.printf("%s는 한장의 카드를 더 받겠습니다?(예는 y, 아니오는 n)%n", player.getName());
        YesOrNo input = YesOrNo.from(scanner.nextLine());
        return input == YesOrNo.YES;
    }

    public static List<Integer> inputBets(List<String> names) {
        List<Integer> bets = new ArrayList<>();
        for (String name : names) {
            System.out.printf("%s의 배팅 금액은?%n", name);
            String input = scanner.nextLine();
            validateInteger(input);
            bets.add(Integer.parseInt(input));
        }
        return bets;
    }

    private static void validateInteger(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("정수를 입력해주세요.");
        }
    }
}