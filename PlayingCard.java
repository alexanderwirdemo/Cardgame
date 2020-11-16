public class PlayingCard {
    int cardNo; //denna variabel representerar ett av lekens 52 kort och är unik för varje kort
    Suits suit; //denna variabel representerar vilken svit kortet har (Hjärter, Spader, Klöver, Ruter)
    int value; //denna variabel representerar vilken valör kortet har (2-10, Knekt, Dam, Kung, Ess, där 11-Knekt, 12-Dam, 13-Kung och 1-Ess)
    boolean isVisible; //denna variebel representerar om kortet är synligt eller ej
    FaceValue faceValue; //denna variabel representerar kortets valör med enheter som vi är med vana vid

    public PlayingCard(int cardNo, Suits suit, int value, boolean isVisible) {
        this.cardNo = cardNo;
        this.suit = suit;
        this.value = value;
        this.faceValue = setFaceValue(value); //Kortet får en mer lättolkad valör, som vi är mer vana vid
        this.isVisible = isVisible;
    }

    public FaceValue setFaceValue(int value) {
        if(value==1){
            faceValue = FaceValue.Ess;
        }
        else if(value==2){
            faceValue = FaceValue.Två;
        }
        else if(value==3){
            faceValue = FaceValue.Tre;
        }
        else if(value==4){
            faceValue = FaceValue.Fyra;
        }
        else if(value==5){
            faceValue = FaceValue.Fem;
        }
        else if(value==6){
            faceValue = FaceValue.Sex;
        }
        else if(value==7){
            faceValue = FaceValue.Sju;
        }
        else if(value==8){
            faceValue = FaceValue.Åtta;
        }
        else if(value==9){
            faceValue = FaceValue.Nio;
        }
        else if(value==10){
            faceValue = FaceValue.Tio;
        }
        else if(value==11){
            faceValue = FaceValue.Knekt;
        }
        else if(value==12){
            faceValue = FaceValue.Dam;
        }
        else if(value==13){
            faceValue = FaceValue.Kung;
        }
        return faceValue;
    }
}
