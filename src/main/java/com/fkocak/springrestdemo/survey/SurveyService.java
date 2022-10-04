package com.fkocak.springrestdemo.survey;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class SurveyService {

    private static List<Survey> surveys = new ArrayList<>();

    static {

        Question question1 = new Question(
                "Question1",
                "Most Popular Cloud Platform Today",
                Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"),
                "AWS");
        Question question2 = new Question(
                "Question2",
                "Fastest Growing Cloud Platform",
                Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"),
                "Google Cloud");
        Question question3 = new Question(
                "Question3",
                "Most Popular DevOps Tool",
                Arrays.asList("Kubernetes", "Docker", "Terraform", "Azure DevOps"),
                "Kubernetes");

        List<Question> questions = new ArrayList<>(Arrays.asList(question1, question2, question3));

        Survey survey = new Survey(
                "Survey1",
                "My Favorite Survey",
                "Description of the Survey",
                questions);

        surveys.add(survey);

    }


    public List<Survey> retrieveAllSurveys() {
        return surveys;
    }

    public Survey retrieveSurvey(String surveyID){

        Predicate<? super Survey> predicate =
                survey -> survey.getId().equals(surveyID);
        Optional<Survey> optionalSurvey = surveys.stream().filter(predicate).findFirst();

        return optionalSurvey.orElse(null);
    }

    public List<Question> retrieveAllQuestions(String surveyID) {
        return retrieveSurvey(surveyID).getQuestions();
    }

    public Question retrieveQuestion(String surveyID,String questionID) {
        Predicate<? super Question> predicate =
                question -> question.getId().equals(questionID);
        Optional<Question> optionalQuestion = retrieveAllQuestions(surveyID).stream().filter(predicate).findFirst();

        return optionalQuestion.orElse(null);
    }


    public String addNewQuestion(String surveyID, Question question) {
        List<Question> questions = retrieveAllQuestions(surveyID);


        question.setId(generateRandomId());

        questions.add(question);

        //We are returning question ID to redirect the pace to the created question
        //It also could be void to just make the POST done, so this is an additional feature
        return question.getId();
    }

    //Random ID generator
    public String generateRandomId(){
        SecureRandom secureRandom = new SecureRandom();
        String randomID = new BigInteger(32, secureRandom).toString();
        return randomID;
    }

    //DELETE question with ID
    public String deleteQuestion(String surveyID, String questionID) {
        List<Question> questions = retrieveAllQuestions(surveyID);

        Predicate<? super Question> predicate =
                question -> question.getId().equals(questionID);

        boolean removed = questions.removeIf(predicate);

        if (!removed) return null;
        return questionID;
    }

    //PUT (update) question with ID
    public void updateQuestion(String surveyID, String questionID, Question question) {
        List<Question> questions = retrieveAllQuestions(surveyID);

        Predicate<? super Question> predicate =
                oldQuestion -> oldQuestion.getId().equals(questionID);

        questions.removeIf(predicate);
        questions.add(question);
    }
}
