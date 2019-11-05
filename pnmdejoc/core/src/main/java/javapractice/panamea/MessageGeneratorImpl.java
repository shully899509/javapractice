package javapractice.panamea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class MessageGeneratorImpl implements MessageGenerator {
    private static final Logger log = LoggerFactory.getLogger(MessageGeneratorImpl.class);

    @Autowired
    private Game game;
    private int guessCount = 10;

    @PostConstruct
    public void check(){
        log.debug("value of game {}", game);
    }

    @Override
    public String getMainMessage() {
        return "Number is between " + game.getSmallest() + " - " + game.getBiggest() + ". Ghiceste-l in panamea?!?";
    }

    @Override
    public String getResultMessage() {
        if (game.isGameWon()){
            return "DA in panamea. Era " + game.getNumber();
        } else if (game.isGameLost()) {
            return "NU in pana mea. Era " + game.getNumber();
        } else if (!game.isValidNumberRange()) {
            return "Invalid range!";
        } else if (game.getRemainingGuesses() == guessCount){
            return "What is ur first guess";
        } else {
            String direction = "Lower";

            if (game.getGuess() < game.getNumber()){
                direction = "Higher";
            }

            return direction + "! Mai ai " + game.getRemainingGuesses() + " incercari in panamea!";
        }

    }
}
