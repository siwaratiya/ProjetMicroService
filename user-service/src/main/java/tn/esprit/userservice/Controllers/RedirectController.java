package tn.esprit.userservice.Controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/biochar")
public class RedirectController {
    // GET method
    @GetMapping("/**")
    //@PreAuthorize("hasRole('Chief_Service')")
    //@PreAuthorize("hasRole('Patient')")
    public String handleGetRequest() {
        return "This is a GET request.";
    }
    // POST method
    @PostMapping("/**")
    public String handlePostRequest() {
        return "This is a POST request.";
    }
    // PUT method
    @PutMapping("/**")
    public String handlePutRequest() {
        return "This is a PUT request.";
    }
    // DELETE method
    @DeleteMapping("/**")
    public String handleDeleteRequest() {
        return "This is a DELETE request.";
    }
}