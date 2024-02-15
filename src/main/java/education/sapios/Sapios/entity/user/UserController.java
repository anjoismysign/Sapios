package education.sapios.Sapios.entity.user;

import education.sapios.Sapios.entity.course.SapioUserCourse;
import education.sapios.Sapios.entity.course.UnitRepository;
import education.sapios.Sapios.entity.course.UserUnitRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    private final UserUnitRepository userUnitRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository,
                          UnitRepository unitRepository,
                          UserUnitRepository userUnitRepository,
                          UserService userService) {
        this.userRepository = userRepository;
        this.unitRepository = unitRepository;
        this.userUnitRepository = userUnitRepository;
        this.userService = userService;
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<List<SapioUserCourse>> getCoursesForUser(@PathVariable Long id) {
        UserEntity user = userRepository.findById(id)
                .orElse(null);
        if (user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userService.getCoursesForUser(user));
    }

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity userEntity) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setEmail(userEntity.getEmail());
                    existingUser.setHashedPassword(userEntity.getHashedPassword());
                    existingUser.setFirstName(userEntity.getFirstName());
                    existingUser.setLastName(userEntity.getLastName());
                    existingUser.setBiography(userEntity.getBiography());
                    existingUser.setSocialMedia(userEntity.getSocialMedia());
                    return ResponseEntity.ok(userRepository.save(existingUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * Post methods
     */

    /**
     * In order to call this from front end, you must post a request to
     * /api/users/register with a UserEntity as the request body.
     * The UserEntity should have the email and hashedPassword fields,
     * and the rest can be null.
     *
     * @param userEntity The UserEntity to save
     * @return The UserEntity if saved, or 400 if already exists
     */
    @PostMapping("/register")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity) {
        if (userRepository.findByEmail(userEntity.getEmail()).isPresent())
            return ResponseEntity.badRequest().build();
        else {
            return ResponseEntity.ok(userRepository.save(userEntity));
        }
    }

    /**
     * In order to call this from front end, you must post a request to
     * /api/users/login with a UserEntity as the request body.
     * The UserEntity should have the email and hashedPassword fields
     * set to the email and hashed password to match.
     *
     * @param userEntity The UserEntity to match
     * @return The UserEntity if found, or 404 if not found
     */
    @PostMapping("/login")
    public ResponseEntity<UserEntity> loginUser(@RequestBody UserEntity userEntity) {
        UserEntity repoUser = userRepository.findByEmail(userEntity.getEmail())
                .orElse(null);
        if (repoUser == null)
            return ResponseEntity.notFound().build();
        if (!BCrypt.checkpw(userEntity.getHashedPassword(), repoUser.getHashedPassword()))
            return ResponseEntity.status(401).build();
        return ResponseEntity.ok(repoUser);
    }

    @PostMapping("/courses")
    public ResponseEntity<List<SapioUserCourse>> getCoursesForUser(@RequestBody UserEntity userEntity) {
        UserEntity user = userRepository.findById(userEntity.getId())
                .orElse(null);
        if (user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userService.getCoursesForUser(user));
    }
}