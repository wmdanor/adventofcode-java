package org.example.day2;

public class SimpleRoundStrategy {
    private final Shapes opponentShape;
    private final Shapes yourShape;
    private final RoundResult roundResult;

    public SimpleRoundStrategy(Shapes opponentShape, Shapes yourShape) {

        this.opponentShape = opponentShape;
        this.yourShape = yourShape;

        this.roundResult = switch (opponentShape) {
            case Rock -> switch (yourShape) {
                case Rock -> RoundResult.Draw;
                case Paper -> RoundResult.Win;
                case Scissors -> RoundResult.Loss;
            };
            case Paper -> switch (yourShape) {
                case Rock -> RoundResult.Loss;
                case Paper -> RoundResult.Draw;
                case Scissors -> RoundResult.Win;
            };
            case Scissors -> switch (yourShape) {
                case Rock -> RoundResult.Win;
                case Paper -> RoundResult.Loss;
                case Scissors -> RoundResult.Draw;
            };
        };
    }

    public Shapes getOpponentShape() {
        return opponentShape;
    }

    public Shapes getYourShape() {
        return yourShape;
    }

    public RoundResult getRoundResult() {
        return roundResult;
    }

    public int getYourShapeScore() {
        return switch (yourShape) {
            case Rock -> 1;
            case Paper -> 2;
            case Scissors -> 3;
        };
    }

    public int getRoundResultScore() {
        return switch (roundResult) {
            case Win -> 6;
            case Draw -> 3;
            case Loss -> 0;
        };
    }
}
