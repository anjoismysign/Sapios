package education.sapios.Sapios.controller;

import education.sapios.Sapios.entity.hallway.Course;
import education.sapios.Sapios.service.CourseImportExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/courses")
public class CourseApiController {
    
    @Autowired
    private CourseImportExportService importExportService;
    
    /**
     * Export a course to JSON
     * 
     * @param courseId The ID of the course to export
     * @return JSON file for download
     */
    @GetMapping("/{courseId}/export")
    public ResponseEntity<byte[]> exportCourse(@PathVariable Long courseId) {
        try {
            String json = importExportService.exportCourse(courseId);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setContentDispositionFormData("attachment", "course_" + courseId + ".json");
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(json.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(("Error exporting course: " + e.getMessage()).getBytes());
        }
    }
    
    /**
     * Import a course from a JSON file
     * 
     * @param file The JSON file to import
     * @return Success or error message
     */
    @PostMapping("/import")
    public ResponseEntity<String> importCourse(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }
        
        try {
            String json = new String(file.getBytes(), StandardCharsets.UTF_8);
            Course course = importExportService.importCourse(json);
            return ResponseEntity.ok("Course '" + course.getName() + "' imported successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error importing course: " + e.getMessage());
        }
    }
}
