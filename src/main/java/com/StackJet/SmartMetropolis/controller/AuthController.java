package com.StackJet.SmartMetropolis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.List;
import com.StackJet.SmartMetropolis.dto.UserDto;
import com.StackJet.SmartMetropolis.entity.Review;
import com.StackJet.SmartMetropolis.entity.User;
import com.StackJet.SmartMetropolis.service.ReviewService;
import com.StackJet.SmartMetropolis.service.UserService;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;
    private final ReviewService reviewService;

    @Autowired
    public AuthController(UserService userService, ReviewService reviewService) {
        this.userService = userService;
        this.reviewService = reviewService;
    }

    // Method to fetch the logged-in user's name
    private String getLoggedInUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // Get the logged-in user's email
        User user = userService.findUserByEmail(email); // Fetch user by email
        return user != null ? user.getName() : "Guest"; // Return the user's name or default to 'Guest'
    }

    @GetMapping("/index")
    public String index(Model model) {
        // Retrieve the logged-in user's name
        String username = getLoggedInUserName();

        // Retrieve the list of reviews
        List<Review> reviews = reviewService.getAllReviews();

        // Add both username and reviews to the model
        model.addAttribute("username", username);
        model.addAttribute("reviews", reviews);

        // Return the index.html view
        return "index";
    }

    // Other methods remain the same...

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/auth/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/hospital")
    public String hospital() {
        return "hospital";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/transport")
    public String transport() {
        return "transport";
    }

    @GetMapping("/education")
    public String education() {
        return "education";
    }

    @GetMapping("/schools")
    public String schools() {
        return "schools";
    }

    @GetMapping("/Theater")
    public String theater() {
        return "Theater";
    }

    @GetMapping("/tourism")
    public String tourism() {
        return "tourism";
    }

    @GetMapping("/TouristSpot")
    public String touristSpot() {
        return "TouristSpot";
    }

    @GetMapping("/map")
    public String map() {
        return "map";
    }

    @GetMapping("/translator")
    public String translator() {
        return "translator";
    }
    
    @GetMapping("/tsd")
    public String tsd() {
        return "tsd";
    }

    @GetMapping("/registration_data")
    public String viewRegistrationData(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "registration_data";
    }
}