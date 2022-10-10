package com.fkocak.springrestdemo.survey;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class SurveyResource {

    private SurveyService surveyService;
    //= new SurveyService(); Spring handles instances so we dont need to create a new object everytime

    public SurveyResource(SurveyService surveyService) {
        this.surveyService = surveyService;
    } // Giving he desired object as constructor parameter is enough to get the object in Spring

    @RequestMapping("/surveys")
    public List<Survey> retrieveAllSurveys() { return surveyService.retrieveAllSurveys(); }

    @RequestMapping("/surveys/{surveyID}")
    public Survey retrieveSurvey(@PathVariable String surveyID) {
        Survey survey = surveyService.retrieveSurvey(surveyID);

        if (survey != null) return survey;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/surveys/{surveyID}/questions")
    public List<Question> retrieveAllQuestions(@PathVariable String surveyID) {
        List<Question> questions = surveyService.retrieveAllQuestions(surveyID);

        if (questions != null) return questions;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/surveys/{surveyID}/questions/{questionID}")
    public Question retrieveQuestion(@PathVariable String surveyID, @PathVariable String questionID) {
        Question question = surveyService.retrieveQuestion(surveyID, questionID);

        if (question != null) return question;
        // 404 not found error to show something instead of blank page on null returns
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    //Using POST method to add a new question to survey
    //All the url content is used to send GET method
    //Right now we need REST API tester to send POST requests
    @RequestMapping(value = "/surveys/{surveyID}/questions", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewQuestion(
            @PathVariable String surveyID, //To get surveyID from GET method in url
            @RequestBody Question question //To capture the request body as POST
    ) {
        String questionID = surveyService.addNewQuestion(surveyID, question);


        //UriComponentsBuilder with additional static factory methods to create links
        //based on the current HttpServletRequest.
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{questionID}")
                .buildAndExpand(questionID)
                .toUri();


        //Using this return statement to change the response code from success(200) to created(201)
        //also to return the location uri to the regarding question
        //for example = http://localhost:8080/surveys/Survey1/questions/3786913489
        return ResponseEntity.created(location).build();
    }

    //DELETE question with ID
    @RequestMapping(value = "/surveys/{surveyID}/questions/{questionID}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteQuestion(
            @PathVariable String surveyID,
            @PathVariable String questionID
    ) {
        surveyService.deleteQuestion(surveyID, questionID);
        return ResponseEntity.noContent().build();
    }

    //PUT(update) question with ID
    @RequestMapping(value = "/surveys/{surveyID}/questions/{questionID}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateQuestion(
            @PathVariable String surveyID,
            @PathVariable String questionID,
            @RequestBody Question question
    ) {
        surveyService.updateQuestion(surveyID, questionID, question);
        return ResponseEntity.noContent().build();
    }

}
