import java.util.ArrayList;
import java.util.Random;

public class PlayingCardDeck {
    //Konstanter
    static final int noOfCards = 52; //denna konstant anger hur många kort som skall finnas i kortleken

    //Variabler
    static ArrayList<PlayingCard> allCards=new ArrayList<>(); //Denna ArrayList representerar alla kort
    static ArrayList<Integer> unshuffledDeck = new ArrayList<>(); //Denna ArrayList representerar också alla kort, oblandade
    static ArrayList<Integer> shuffledDeck = new ArrayList<>(); //Denna ArrayList representerar den blandade kortleken
    static Random random = new Random();

    public static void main(String[] args){
        generateDeck();
        System.out.println(getTopCard(shuffledDeck));
        printDeck(shuffledDeck);
        moveCard(0);
        System.out.println();
        printDeck(shuffledDeck);

    }
    public static void generateDeck(){ //den här metoden genererar kortleken
        for (int cardNo=1;cardNo<=noOfCards;cardNo++){
            if(cardNo<=13) {
                PlayingCard card = new PlayingCard(cardNo, Suits.KLÖVER, cardNo, true);
                allCards.add(card);
                unshuffledDeck.add(cardNo);
            }
            else if(cardNo>13 && cardNo<=26){
                PlayingCard card = new PlayingCard(cardNo, Suits.RUTER, cardNo-13, true);
                allCards.add(card);
                unshuffledDeck.add(cardNo);
            }
            else if(cardNo>26 && cardNo<=39){
                PlayingCard card = new PlayingCard(cardNo, Suits.HJÄRTER, cardNo-26, true);
                allCards.add(card);
                unshuffledDeck.add(cardNo);
            }
            else if(cardNo>39 && cardNo<=noOfCards){
                PlayingCard card = new PlayingCard(cardNo, Suits.SPADER, cardNo-39, true);
                allCards.add(card);
                unshuffledDeck.add(cardNo);
            }
        }
        shuffledDeck=shuffleDeck(unshuffledDeck);
    }
    public static void printDeck(ArrayList<Integer> shuffledDeck){
        for(int cardIndex=0;cardIndex<shuffledDeck.size();cardIndex++){
            System.out.print(allCards.get(shuffledDeck.get(cardIndex)-1).suit+" "+allCards.get(shuffledDeck.get(cardIndex)-1).value+"\n");
        }
    }

    public static int getTopCard(ArrayList<Integer> shuffledDeck){ //Denna metod returnerar kortet högst upp i leken
        int topCard = -1;
        if(shuffledDeck.size()!=0) {
            topCard = shuffledDeck.get(0);
        }
        else {
            System.out.println("Kortleken är tom, det går inte att dra fler kort!");
        }
        return topCard;
    }

    public static void moveCard(int cardIndex){ //Denna metod flyttar ett kort
        shuffledDeck.add(shuffledDeck.get(cardIndex));
        shuffledDeck.remove(cardIndex);
    }

    public static ArrayList<Integer> shuffleDeck(ArrayList<Integer> unshuffledDeck){ //Denna metod blandar kortleken
        for(int cardIndex=0;cardIndex<noOfCards;cardIndex++) {
            int randomCard = random.nextInt(unshuffledDeck.size());
            shuffledDeck.add(unshuffledDeck.get(randomCard));
            unshuffledDeck.remove(randomCard);
        }
        return shuffledDeck;
    }

}
