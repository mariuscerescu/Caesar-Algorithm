import java.io.IOException;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class Controller {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private boolean isError;

    @FXML
    private TextField cheia1Label, cheia2Label, textLabel, 
                      DecryptionTextField, cheia1DecryptionTextField, cheia2DecryptionTextField;

    @FXML
    private Label errorLabel, mesajLabel, mesajDecriptatLabel;

    @FXML
    void goToSceneCripteaza(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("CriptareScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void goToSceneDecripteaza(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("DecriptareScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void goToMainScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void cripteaza(ActionEvent event) {
        isError = false;
        errorLabel.setText("_");
    
        if(!textLabel.getText().isEmpty() && !cheia1Label.getText().isEmpty() && !cheia2Label.getText().isEmpty()){
            String text = textLabel.getText();
            String cheia2 = cheia2Label.getText();
            int cheia1 = 0;
    
            text = processString(text);
            cheia2 = processString(cheia2);
    
            try{
                cheia1 = Integer.parseInt(cheia1Label.getText());
            }
            catch(Exception e){
                errorLabel.setText("Cheia 1 trebuie sa fie un numar");
                isError = true;
            }
    
            if(isError == false){
                // Sort the characters in the second key
                char[] chars = cheia2.toCharArray();
                Arrays.sort(chars);
                String sortedCheia2 = new String(chars);
    
                // Encrypt the text using the sorted second key
                mesajLabel.setText(encrypt(text, cheia1, sortedCheia2));
                System.out.print(encrypt(text, cheia1, sortedCheia2) + "\n\n");
            }
    
        }else{
            errorLabel.setText("Completează toate câmpurile");
        }
    }

    @FXML
    void decripteaza(ActionEvent event) {
        isError = false;
        errorLabel.setText("_");

        if(!DecryptionTextField.getText().isEmpty() && !cheia1DecryptionTextField.getText().isEmpty() && !cheia2DecryptionTextField.getText().isEmpty()){
            String text = DecryptionTextField.getText();
            String cheia2 = cheia2DecryptionTextField.getText();
            int cheia1 = 0;

            text = processString(text);
            cheia2 = processString(cheia2);

            try{
                cheia1 = Integer.parseInt(cheia1DecryptionTextField.getText());
            }
            catch(Exception e){
                errorLabel.setText("Cheia 1 trebuie sa fie un numar");
                isError = true;
            }

            if(isError == false){
                mesajDecriptatLabel.setText(decrypt(text, cheia1, cheia2));
            }

        }else{
            errorLabel.setText("Completează toate câmpurile");
        }
    }

    String processString(String input) {
        // Remove all spaces from the input string
        String noSpaces = input.replaceAll(" ", "");
    
        // Check if the remaining string contains any non-letter characters
        if (!noSpaces.matches("[a-zA-Z]+")) {
            errorLabel.setText("Textul si cheia 2 trebuie sa contina numai litere");
            isError = true;
        }
    
        // Convert the remaining characters to uppercase and return the result
        return noSpaces.toUpperCase();
    }

    String encrypt(String str, int key, String secondKey) {
        StringBuilder result = new StringBuilder();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String shiftedAlphabet = secondKey + alphabet.replace(secondKey, "");
        for (char ch : str.toCharArray()) {
            if (Character.isLetter(ch)) {
                char shiftedChar = shiftedAlphabet.charAt((alphabet.indexOf(Character.toLowerCase(ch)) + key) % 26);
                result.append(Character.isUpperCase(ch) ? Character.toUpperCase(shiftedChar) : shiftedChar);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    String decrypt(String str, int key, String secondKey) {
        StringBuilder result = new StringBuilder();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String shiftedAlphabet = secondKey + alphabet.replace(secondKey, "");
        for (char ch : str.toCharArray()) {
            if (Character.isLetter(ch)) {
                char shiftedChar = alphabet.charAt((shiftedAlphabet.indexOf(Character.toLowerCase(ch)) - key + 26) % 26);
                result.append(Character.isUpperCase(ch) ? Character.toUpperCase(shiftedChar) : shiftedChar);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }
    

}
