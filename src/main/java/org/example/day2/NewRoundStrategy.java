package org.example.day2;

public class NewRoundStrategy {
    private final Shapes opponentShape;
    private final Shapes yourShape;
    private final RoundResult roundResult;

    public NewRoundStrategy(Shapes opponentShape, RoundResult roundResult) {
        this.opponentShape = opponentShape;
        this.roundResult = roundResult;

        this.yourShape = switch (roundResult) {
            case Win -> switch (opponentShape) {
                case Rock -> Shapes.Paper;
                case Paper -> Shapes.Scissors;
                case Scissors -> Shapes.Rock;
            };
            case Draw -> opponentShape;
            case Loss -> switch (opponentShape) {
                case Rock -> Shapes.Scissors;
                case Paper -> Shapes.Rock;
                case Scissors -> Shapes.Paper;
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
