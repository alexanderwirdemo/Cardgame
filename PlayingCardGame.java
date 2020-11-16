import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class PlayingCardGame {
    //Variabler
    static ArrayList<Integer>gameDeck=new ArrayList<>(); //I den här ArrayList:en finns en referens till alla kort som inte finns hos någon spelare, 'sjön'
    static ArrayList<Integer>playersHand=new ArrayList<>(); //I denna ArrayList finns en referens till alla kort som spelaren har på hand
    static ArrayList<Integer>opponentsHand=new ArrayList<>(); //I denna ArrayList finns en referens till alla kort som motspelaren har på hand
    static boolean session; //den här variebeln styr ifall menyn skall visas eller ej, och stängs av när spelaren avslutar programmet
    static boolean gameSession; //Den här variabeln styr ifall spelet är igång, och stängs av när spelet är över
    static boolean opponentHasCard; //Denna variebel avgör ifall motståndaren har det kort man frågar om på hand eller ej
    static boolean playerHasCard; //Denna variebel avgör ifall spelaren har det kort man frågar om på hand eller ej
    static Scanner scanner = new Scanner(System.in);
    static int quadrupels = 0; //Denna variebel räknar hur många fyrtal det finns totalt
    static int quadrupelsOpponent = 0; //Denna variebel räknar hur många fyrtal motståndaren har totalt
    static int quadrupelsTotal = 0; //Denna variebel räknar hur många fyrtal spelaren har totalt
    static ArrayList<Integer>quadrupelsValue=new ArrayList<>(); //Denna lista sparar alla fyrtal som spelaren har samlat
    static ArrayList<Integer>quadrupelsValueOpponent=new ArrayList<>(); //Denna lista sparar alla fyrtal som motspelaren har samlat
    static ArrayList<Integer>cardsAskedByOpponent=new ArrayList<>();
    static Random random = new Random();
    public static void main(String[] args) {
        session = true;
        while (session == true) {
            switch (menu()) {
                case 1:
                    rules(); //reglerna förklaras
                    break;
                case 2:
                    gameSession = true;
                    PlayingCardDeck.generateDeck(); //En kortlek skapas med hjälp av klassen PlayingCardDeck
                    gameDeck = PlayingCardDeck.shuffleDeck(PlayingCardDeck.shuffledDeck); //En blandad kortlek skapas med hjälp av klseen PlayingCardDeck
                    dealStartingHand(gameDeck, playersHand); //3 kort delas ut till spelaren
                    dealStartingHand(gameDeck, opponentsHand); //3 kort delas ut till motståndaren
                    System.out.println();
                    while (gameSession == true) {
                        //Här börjar spelarens tur:
                        System.out.println();
                        System.out.println("---Spelarens tur!---");
                        do {
                            checkForQuads(playersHand); //Det första som sker är att man kollar ifall man har något fyrtal.
                            printPlayersHand(playersHand); //Spelarens kort visas på skärmen
                            System.out.println();
                            do {
                                if(playersHand.size()>0) {
                                    System.out.print("Välj kort att söka efter (1-Ess; 2-10-Två-Tio; 11-Knekt; 12-Dam; 13-Kung): ");
                                    int value = scanner.nextInt();
                                    if (!(value > 0 && value <= 13))
                                        System.out.println("Det kan du inte fråga efter!");
                                    else {
                                        playerHasCard = searchPlayersHand(value, playersHand);
                                        if (playerHasCard == true) { //Man får inte fråga efter kort man inte har
                                            playersRound(value); //Om man har frågat 'rätt', kollas detta mot motspelarens kort i metoden playersRund
                                        } else {
                                            System.out.println("Det kortet har du inte, gissa ett annat kort!");
                                        }
                                    }
                                }
                                else if(gameDeck.size()>0){ //Utifall man inte har något kort alls på hand, får man ett nytt kort
                                    dealCard(gameDeck, playersHand);
                                }
                            } while (playerHasCard == false);
                            if (quadrupelsTotal == 13) { //spelets slutvillkor
                                gameOver();
                                gameSession = false;
                                opponentHasCard = false;
                                quadrupelsTotal = 0;
                                quadrupelsOpponent=0;
                                quadrupels=0;
                                quadrupelsValue.clear();
                                quadrupelsValueOpponent.clear();
                                break;
                            }
                        } while (opponentHasCard == true); //Man får fortsätta fråga så länge motstånadren har det kort man frågar efter

                        //Här börjar motståndarens tur:
                        if(gameSession==true) { //Detta villkor är med utifall spelaren avslutar spelet på sin tur. Då behöver detta inte skrivas ut.
                            System.out.println();
                            System.out.println("---Motståndarens tur!---");
                        }
                        do {
                            playerHasCard = false; //default-värde för att undvika 'idiot-loopar'
                            if(gameSession==true) {
                                checkForQuads(opponentsHand);
                                printOpponentsHand(opponentsHand);
                                System.out.println();
                            }
                            if (quadrupelsTotal < 13) {
                                if(opponentsHand.size()>0) {
                                    int opponentsValue = generateRandomNumber(opponentsHand); //Ett slumpmässigt kort som motståndaren har genereras
                                    System.out.print("Motståndaren valde: " + opponentsValue);
                                    System.out.println();
                                    playerHasCard = searchPlayersHand(opponentsValue, playersHand); //Man kollar ifall spelaren har kortet
                                    if (playerHasCard == true) {
                                        System.out.println("Du gav följande kort till motståndaren:");
                                        giveCards(opponentsValue, opponentsHand, playersHand); //korten byter ägare
                                    } else if (gameDeck.size() > 0) {
                                        System.out.println("Finns i sjön!");
                                        dealCard(gameDeck, opponentsHand); //Ett kort dras från 'sjön'
                                        cardsAskedByOpponent.clear();
                                    } else {
                                        System.out.println("Kortleken slut, fortsätter gissa"); //Utifall det inte finns några fler kort i 'sjön'
                                    }
                                }
                                else if(gameDeck.size()>0){
                                    dealCard(gameDeck, opponentsHand); //detta inträffar om motståndaren inte har några kort kvar men det finns kort kvar på bortet
                                }
                            }

                            checkForQuadsOpponent(opponentsHand);
                            if (quadrupelsTotal == 13) { //Game over-villkor
                                gameOver();
                                gameSession = false;
                                playerHasCard = false;
                                quadrupelsTotal = 0;
                                quadrupelsOpponent=0;
                                quadrupels=0;
                                break;
                            }

                        } while (playerHasCard == true);
                    }
                    break;
                case 3:
                    session = false; //Avslutar programmet
                    break;
                    default:
                        System.out.println("Försök igen");
                        break;

            }
        }


    }

    public static void playersRound(int value){
            if (searchOpponentsHand(value, opponentsHand) == true) {
                System.out.println("Du fick följande kort av motståndaren:");
                giveCards(value, playersHand, opponentsHand); //Korten byter ägare
            } else if (gameDeck.size() > 0) {
                System.out.println("Finns i sjön!");
                dealCard(gameDeck, playersHand); //Ett kort tas från 'sjön'

            } else {
                System.out.println("Kortleken slut, fortsätter gissa");
            }
            checkForQuads(playersHand);
    }

    public static int menu(){
        System.out.println("Välkommen till Alexanders 'Finns i sjön'-spel för Java!");
        System.out.println("1. Se regler");
        System.out.println("2. Spela ett parti");
        System.out.println("3. Avsluta");
        System.out.print("Välj alternativ: ");
        int menuChoice = scanner.nextInt();
        return menuChoice;
    }

    public static void rules(){
        System.out.println("REGLER FÖR SPELET FINNS I SJÖN");
        System.out.print("Spelet går ut på att samla så många fyrtal som möjligt."+"\n"+"Varje spelare börjar med tre kort. " +
                "Sedan frågar man sin motståndare om de har något av korten man själv har med motsvarande värde."+"\n"+
                "Man får inte fråga om någor kort som man inte har på hand. Om motståndaren har något av dessa kort, lämnas kortet/korten över till spelaren och spelaren får fortsätta gissa."+"\n"+
                "Om man gissar fel, svarar motståndaren 'Finns i sjön!' och man får då ta ett kort ut kortleken och lägga till sin hand."+"\n"+
                "Om man får ett fyrtal, läggs det åt sidan och ingår i räkningen hur många fyrtal man har samlat."+"\n"+
        "När alla kort är slut och ingen spelare har något kort kvar på hand räknas varje spelares antal fyrtal ihop och den spelare med flest antal fyrtal vinner."+"\n");
    }

    public static ArrayList<Integer> dealStartingHand (ArrayList<Integer> shuffeledDeck, ArrayList<Integer>hand){
        for(int counter=0;counter<3;counter++){ //kort tas från toppen av kortleken och ges till spelaren med hjälp av getTopCard-metoden i PlayingCardDeck-klassen
            int cardNo = PlayingCardDeck.getTopCard(shuffeledDeck);
            hand.add(cardNo);
            shuffeledDeck.remove(0);
        }
        return playersHand;
    }

    public static ArrayList<Integer> dealCard (ArrayList<Integer> gameDeck, ArrayList<Integer> hand){
        int cardNo = PlayingCardDeck.getTopCard(gameDeck);
        hand.add(cardNo);
        gameDeck.remove(0);
        return hand;
    }

    public static void printPlayersHand(ArrayList<Integer> playersHand){
        System.out.println("Din hand:");
        for(int card=0;card<playersHand.size();card++){
            System.out.print(PlayingCardDeck.allCards.get(playersHand.get(card)-1).suit+" "+PlayingCardDeck.allCards.get(playersHand.get(card)-1).faceValue+" ");
        }
        System.out.println();
        System.out.println("Så här många par har du: "+quadrupels);
        System.out.print("Dessa par har du samlat: ");
        for(int quadrupleNo=0;quadrupleNo<quadrupelsValue.size();quadrupleNo++){
            System.out.print(quadrupelsValue.get(quadrupleNo)+" ");
        }
    }

    public static void printOpponentsHand(ArrayList<Integer> opponentsHand){
        //System.out.println("Motståndarens hand:"); //dessa rader kan man använda om man vill fuska och se motståndarens kort :o) (det användes flitingt under utvecklingen av detta spel för att förstå hur motsåelaren 'tänkte')
        /*for(int card=0;card<opponentsHand.size();card++){
            //PlayingCardDeck.getCard(playersHand.get(card));
            System.out.print(PlayingCardDeck.allCards.get(opponentsHand.get(card)-1).suit+" "+PlayingCardDeck.allCards.get(opponentsHand.get(card)-1).faceValue+" ");
        }*/
        //System.out.println();
        System.out.println("Så här många par har motspelaren: "+quadrupelsOpponent);
        System.out.print("Dessa par har motspelaren samlat: ");
        for(int quadrupleNo=0;quadrupleNo<quadrupelsValueOpponent.size();quadrupleNo++){
            System.out.print(quadrupelsValueOpponent.get(quadrupleNo)+" ");
        }
    }

    public static boolean searchOpponentsHand(int value, ArrayList<Integer>opposingPlayer){ //den här metoden udnersöker ifall motståndaren har det kort man frågar efter
        opponentHasCard = false;
        for(int cardIndex=0;cardIndex<opposingPlayer.size();cardIndex++){
            if(value==PlayingCardDeck.allCards.get(opposingPlayer.get(cardIndex)-1).value){
                opponentHasCard = true;
            }
        }
        return opponentHasCard;
    }

    public static boolean searchPlayersHand(int value, ArrayList<Integer>opposingPlayer){ //den här metoden udnersöker ifall spelaren har det kort man frågar eft
        playerHasCard = false;
        for(int cardIndex=0;cardIndex<opposingPlayer.size();cardIndex++){
            if(value==PlayingCardDeck.allCards.get(opposingPlayer.get(cardIndex)-1).value){
                playerHasCard = true;
            }
        }
        return playerHasCard;
    }

    public static void giveCards(int value, ArrayList<Integer>askingPlayer, ArrayList<Integer>opposingPlayer){ //I den här metoden byter korten ägare
        for(int cardIndex=0;cardIndex<opposingPlayer.size();cardIndex++){
            if(value==PlayingCardDeck.allCards.get(opposingPlayer.get(cardIndex)-1).value){
                System.out.print(PlayingCardDeck.allCards.get(opposingPlayer.get(cardIndex)-1).suit+" "+PlayingCardDeck.allCards.get(opposingPlayer.get(cardIndex)-1).faceValue+" ");
                askingPlayer.add(opposingPlayer.get(cardIndex));
                opposingPlayer.remove(cardIndex);
                cardIndex--;
            }
        }
        System.out.println();
    }

    public static ArrayList<Integer> checkForQuads (ArrayList<Integer> hand){ //Den här metoden undersöker ifall det föreligger ett fyrtal på hand
        for(int cardNo=0;cardNo<hand.size();cardNo++){ //denna for-loop går igenom hela spelarens hand
            int counter = 0;
            int cardValue = PlayingCardDeck.allCards.get(hand.get(cardNo)-1).value;
            for(int index=0;index<hand.size();index++){ //denna for-loop letar efter quadrupels
                if(cardValue==PlayingCardDeck.allCards.get(hand.get(index)-1).value){
                    counter++;
                    if(counter==4){
                        counter=0;
                        addQuadruple(index, hand);
                        System.out.println();
                        System.out.println("Du har ett fyrtal!!");
                        System.out.println();
                        for(int cardIndex=0;cardIndex<hand.size();cardIndex++){  //denna for-loop tar bort quadrupeln
                            if(cardValue==PlayingCardDeck.allCards.get(hand.get(cardIndex)-1).value){
                                hand.remove(cardIndex);
                                cardIndex--;
                            }
                        }
                    }
                }

            }

        }
        return hand;
    }
    public static int addQuadruple(int index, ArrayList<Integer> hand){ //Ifall fyrtal föreligger, sparas värdet genom denna metod
        quadrupelsValue.add(PlayingCardDeck.allCards.get(hand.get(index)-1).value);
        quadrupels++;
        quadrupelsTotal++;
        return quadrupels;
    }

    public static ArrayList<Integer> checkForQuadsOpponent (ArrayList<Integer> hand){
        for(int cardNo=0;cardNo<hand.size();cardNo++){ //denna for-loop går igenom hela spelarens hand
            int counter = 0;
            int cardValue = PlayingCardDeck.allCards.get(hand.get(cardNo)-1).value;
            for(int index=0;index<hand.size();index++){ //denna for-loop letar efter quadrupels
                if(cardValue==PlayingCardDeck.allCards.get(hand.get(index)-1).value){
                    counter++;
                    if(counter==4){
                        counter=0;
                        addQuadrupleOpponent(index, hand);
                        System.out.println();
                        System.out.println("Motståndaren har ett fyrtal!!");
                        System.out.println();
                        for(int cardIndex=0;cardIndex<hand.size();cardIndex++){  //denna for-loop tar bort quadrupeln
                            if(cardValue==PlayingCardDeck.allCards.get(hand.get(cardIndex)-1).value){
                                hand.remove(cardIndex);
                                cardIndex--;
                            }
                        }
                    }
                }

            }

        }
        return hand;
    }
    public static int addQuadrupleOpponent(int index, ArrayList<Integer> hand){
        quadrupelsValueOpponent.add(PlayingCardDeck.allCards.get(hand.get(index)-1).value);
        quadrupelsOpponent++;
        quadrupelsTotal++;
        return quadrupels;
    }

    public static int generateRandomNumber(ArrayList<Integer> opponentsHand){
        int randomNumber=random.nextInt(opponentsHand.size());
        int value=PlayingCardDeck.allCards.get(opponentsHand.get(randomNumber)-1).value;
        return value;
    }

    public static void gameOver(){ //Den här metoden räknar ihop resultatet och avslutar spelet
        System.out.println("Spelet slut!");
        if(quadrupels>quadrupelsOpponent){
            System.out.println("Grattis, du vann!");
        }
        else{
            System.out.println("Tyvärr, du förlorade. Bättre lycka nästa gång!");
        }
        System.out.println("Så här många par fick du: " + quadrupels);
        System.out.print("Dessa par har du samlat: ");
        for (int quadrupleNo = 0; quadrupleNo < quadrupelsValue.size(); quadrupleNo++) {
            System.out.print(quadrupelsValue.get(quadrupleNo) + " ");
        }
        System.out.println();
        System.out.println("Så här många par fick motspelaren: " + quadrupelsOpponent);
        System.out.print("Dessa par samlade motspelaren: ");
        for (int quadrupleNo = 0; quadrupleNo < quadrupelsValueOpponent.size(); quadrupleNo++) {
            System.out.print(quadrupelsValueOpponent.get(quadrupleNo) + " ");
        }
        System.out.println();
    }

}