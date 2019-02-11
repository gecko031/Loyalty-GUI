package validator;

public class Validator {

    private byte[] matcher = {5, 5, 5, 5};

    public Validator() {

    }

    public boolean verifyPIN(byte[] pattern){
        if(pattern.equals(matcher)){
            return true;
        }else
            return false;

    }
}
