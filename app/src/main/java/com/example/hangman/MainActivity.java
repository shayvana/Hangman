package com.example.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hangman.R;

public class MainActivity extends AppCompatActivity {

    private int numWrong = 0;
    private String lettersGuessed = "";
    private String answer = "";
    private String userAnswer = "";
    private final int NUM_GUESSES = 6;
    private boolean hasLost = false;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // randomly choose a word
        String[] words = getResources().getStringArray(R.array.words);
        int random = (int)(Math.random() * (words.length));
        answer = words[random];

        // show answer for testing purposes
        // set length of letters to be guessed
        userAnswer = String.format("%0" + answer.length() + "d", 0).replace("0", "_ ");

        // set word in activity to the correct number of spaces and underscores
        TextView text = (TextView) findViewById(R.id.word);
        text.setText(userAnswer);

    }

    public void submitAnswer(View view) {
        EditText edit = (EditText) findViewById(R.id.input);
        String userLetter = edit.getText().toString().toLowerCase();
        TextView messages = (TextView) findViewById(R.id.letsplay);
        TextView guessesLeft = (TextView) findViewById(R.id.guessesLeft);
        TextView guesses = (TextView) findViewById(R.id.guesses);

        // check answer has only 1 letter
        if (!hasLost && userLetter.matches("[a-z]") && answer.contains(userLetter)) { // if you guessed right
            if (!lettersGuessed.contains(userLetter)) {
                lettersGuessed = lettersGuessed + userLetter;
                guesses.setText(addSpaces(lettersGuessed));

                // get rid of spaces for now
                userAnswer = userAnswer.replace(" ", "");

                // replace with letters at correct spots
                String temp = "";
                for (int i = 0; i < answer.length(); i++) {
                    if ((answer.charAt(i) == userLetter.charAt(0)) || (answer.charAt(i) == userAnswer.charAt(i))) {
                        temp += answer.charAt(i);
                    } else if (answer.charAt(i) != userLetter.charAt(0)) {
                        temp += "_";
                    } else {
                        temp += userLetter;
                    }
                }
                userAnswer = temp;

                //checks if the user won
                if (userAnswer.trim().equals(answer.trim())) {
                    messages.setText("You won!");
                } else {
                    messages.setText(userLetter + " is right! ");
                }
                //says that the letter is right
                // returns the spaces
                userAnswer = addSpaces(userAnswer);
                // updates the word
                TextView text = (TextView) findViewById(R.id.word);
                text.setText(userAnswer);
            } else {
                messages.setText("You've already guessed " + userLetter + "!");
            } //what happens when u put in the same letter

        } else if (!hasLost && userLetter.matches("[a-z]")){ // you guessed wrong!
            if (!lettersGuessed.contains(userLetter)) {
                lettersGuessed = lettersGuessed + userLetter;
                guesses.setText(addSpaces(lettersGuessed));
                numWrong = numWrong + 1;
                guessesLeft.setText("You have guessed (" + (NUM_GUESSES - numWrong) + " wrong guesses left)");
                messages.setText(userLetter + " is wrong!");
                ImageView img = (ImageView) findViewById(R.id.hangmanpic);
                switch (numWrong) {
                    case 1:
                        img.setImageResource(R.drawable.hangman5);
                        break;
                    case 2:
                        img.setImageResource(R.drawable.hangman4);
                        break;
                    case 3:
                        img.setImageResource(R.drawable.hangman3);
                        break;
                    case 4:
                        img.setImageResource(R.drawable.hangman2);
                        break;
                    case 5:
                        img.setImageResource(R.drawable.hangman1);
                        break;
                    case 6:
                        img.setImageResource(R.drawable.hangman0);
                        messages.setText("The word was " + answer);
                        hasLost = true; //the pictures go backwards so that way the hangman adds on body parts if the person gets stuff wrong
                        break;
                    default:
                        messages.setText("You already lost!");
                }
            } else {
                messages.setText("You've already guessed " + userLetter + "!");
            }
            // update hangman image
        } else if (!hasLost){
            messages.setText("Only one letter allowed!");
        } else {
            messages.setText("You already lost!");
        }
        edit.setText("");
    }


    public void restart(View view) {
        numWrong = 0;
        lettersGuessed = "";
        // randomly choose a word
        String[] words = getResources().getStringArray(R.array.words);
        int random = (int)(Math.random() * (words.length));
        answer = words[random];

        // show answer for testing purposes
        // set length of letters to be guessed
        userAnswer = String.format("%0" + answer.length() + "d", 0).replace("0", "_ ");

        TextView messages = (TextView) findViewById(R.id.letsplay);
        messages.setText("Let's play!");

        TextView guessesLeft = (TextView) findViewById(R.id.guessesLeft);
        guessesLeft.setText("You have guessed (" + (NUM_GUESSES - numWrong) + " wrong guesses left)");

        TextView guesses = (TextView) findViewById(R.id.guesses);
        guesses.setText("");

        TextView text = (TextView) findViewById(R.id.word);
        text.setText(userAnswer);

        ImageView img = (ImageView) findViewById(R.id.hangmanpic);
        img.setImageResource(R.drawable.hangman0);
    }

    public int count(String word, char character) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == character)
                count = count + 1;
        }
        return count;
    }


    public String addSpaces(String word) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (i > 0) {
                result.append(" ");
            }
            result.append(word.charAt(i));
        }
        return result.toString();
    }
}