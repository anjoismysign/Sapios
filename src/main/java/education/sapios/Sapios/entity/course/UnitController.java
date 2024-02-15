package education.sapios.Sapios.entity.course;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/units")
public class UnitController {

    private final UnitRepository unitRepository;

    public UnitController(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Unit> createUnit(@RequestBody Unit unit) {
        Course course = unit.getCourse();
        Long id = unit.getId();
        if (id != null && unitRepository.existsById(id) ||
                course != null && unitRepository.findByCourse(course).stream()
                        .anyMatch(u -> u.getName().equals(unit.getName())))
            return ResponseEntity.badRequest().body(null);
        Unit savedUnit = unitRepository.save(unit);
        return ResponseEntity.ok(savedUnit);
    }

    @PostMapping("/findbycourse")
    public ResponseEntity<List<Unit>> findByCourse(@RequestBody Course course) {
        List<Unit> units = unitRepository.findByCourse(course);
        return ResponseEntity.ok(units);
    }
}
