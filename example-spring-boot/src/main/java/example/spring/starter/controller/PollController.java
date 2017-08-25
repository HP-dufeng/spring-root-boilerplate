package example.spring.starter.controller;

import java.net.URI;
import java.util.Optional;
import javax.inject.Inject;
import javax.validation.Valid;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import example.core.entity.Poll;
import example.core.repository.PollRepository;
import example.oauth.JwtAuthenticatedProfile;
import example.service.dto.JwtUserProfile;
import example.service.dto.WUCCUser;
import example.service.dto.error.ErrorDetail;
import example.spring.starter.exception.ResourceNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@Api(value = "polls", description = "Poll API")
public class PollController {

    private static final Log logger = LogFactory.getLog(PollController.class);

    @Inject
    private PollRepository pollRepository;

    protected void verifyPoll(Long pollId) throws ResourceNotFoundException {
        Optional<Poll> p = pollRepository.findById(pollId);
        if(!p.isPresent())
            throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
    }

    @RequestMapping(value = "/polls", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new Poll", notes="The newly created poll Id will be sent in the location response header", response = Void.class)
    @ApiResponses(value = {@ApiResponse(code=201, message="Poll Created Successfully", response=Void.class), @ApiResponse(code=500, message="Error creating Poll", response=ErrorDetail.class) } )
    public ResponseEntity<Void> createPoll(@Valid @RequestBody Poll poll) {
        poll = pollRepository.save(poll);

        // Set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(poll.getId())
                .toUri();

        responseHeaders.setLocation(newPollUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value="/polls", method=RequestMethod.GET)
    @ApiOperation(value = "Retrieves all the polls", response=Poll.class,
            responseContainer="List")
    @PreAuthorize("hasAuthority('superAdmin')")
    public ResponseEntity<Page<Poll>> getAllPolls(Pageable pageable) {

        JwtAuthenticatedProfile authentication = (JwtAuthenticatedProfile) SecurityContextHolder.getContext().getAuthentication();
        WUCCUser currentUser = (WUCCUser)authentication.getPrincipal();
        logger.info("Aauthentied success, current user is: " + currentUser.getName());

        Page<Poll> allPolls = pollRepository.findAll(pageable);
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }


    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves given Poll", response=Poll.class)
    @ApiResponses(value = {@ApiResponse(code=200, message="", response=Poll.class), @ApiResponse(code=404, message="Unable to find Poll", response=ErrorDetail.class) } )
    public ResponseEntity<?> getPoll(@PathVariable Long pollId){
        verifyPoll(pollId);
        Optional<Poll> p = pollRepository.findById(pollId);

        return  new ResponseEntity<>(p, HttpStatus.OK);
    }

    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.PUT)
    @ApiOperation(value = "Updates given Poll", response=Void.class)
    @ApiResponses(value = {@ApiResponse(code=200, message="", response=Void.class), @ApiResponse(code=404, message="Unable to find Poll", response=ErrorDetail.class) } )
    public ResponseEntity<Void> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId){
        verifyPoll(pollId);
        Poll p = pollRepository.save(poll);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes given Poll", response=Void.class)
    @ApiResponses(value = {@ApiResponse(code=200, message="", response=Void.class), @ApiResponse(code=404, message="Unable to find Poll", response=ErrorDetail.class) } )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deletePoll(@PathVariable Long pollId){
        verifyPoll(pollId);
        pollRepository.deleteById(pollId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

