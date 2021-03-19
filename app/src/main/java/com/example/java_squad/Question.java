package com.example.java_squad;

import java.util.ArrayList;
import java.util.List;

/**
 * Question class. models the forum of questions for each experiment in the form of a tree,
 * where replies to each question branch off of each other.
 */
public class Question {
    private String text;
    private List<Question> replies = new ArrayList<>();
}
