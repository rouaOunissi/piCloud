    package com.pi.cours.services;

    import com.pi.cours.dao.CourseDao;
    import com.pi.cours.models.Video;
    import lombok.extern.slf4j.Slf4j;
    import com.pi.cours.models.Course;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.web.multipart.MultipartFile;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.io.IOException;
    import java.nio.file.Files;
    import java.util.List;
    import java.util.Optional;

    @Service
    @Slf4j

    public class CourseServiceImpl implements CourseService {

        @Autowired
        private CourseDao courseDao;

        @Override
        public Course addCourse(Course course, MultipartFile[] files) {
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    String videoPath = storeFile(file);
                    if (videoPath != null) {
                        Video video = new Video();
                        video.setFilePath(videoPath);
                        video.setCourse(course);
                        course.getVideos().add(video);
                        log.info("Added video with path: {}", videoPath);
                    } else {
                        log.error("Failed to store file: {}", file.getOriginalFilename());
                    }
                }
            } else {
                log.warn("No files provided for the course.");
            }
            return courseDao.save(course);
        }




        @Override
        public Course updateCourse(Course course) {
            // Assuming the course ID is set and exists, this will update the existing course
            return courseDao.save(course);
        }
        @Override
        public void deleteCourse(Long id) {
            courseDao.deleteById(id);
        }
        @Override
        public Course getCourseById(Long id) {
            return courseDao.findById(id)
                    .orElseThrow(() -> new RuntimeException("Course not found with id " + id));
        }
        @Override
        public List<Course> getAllCourses() {
            return courseDao.findAll();
        }
        private String storeFile(MultipartFile file) {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + file.getOriginalFilename());
            }
            try {
                // Ensure the directory exists
                String storageDirectoryPath = "D:\\ArcTic2\\piCloudAngularfinal\\uploads";
                Path storageDirectory = Paths.get(storageDirectoryPath);
                if (!Files.exists(storageDirectory)) {
                    Files.createDirectories(storageDirectory);
                }

                // Resolve the file path
                Path destinationFilePath = storageDirectory.resolve(Paths.get(file.getOriginalFilename()))
                        .normalize().toAbsolutePath();

                // Check if the file's path is valid and does not escape the directory
                if (!destinationFilePath.getParent().equals(storageDirectory.toAbsolutePath())) {
                    throw new RuntimeException("Cannot store file outside current directory.");
                }

                // Copy the file to the target location (Replacing existing file with the same name)
                file.getInputStream().transferTo(Files.newOutputStream(destinationFilePath));
                return destinationFilePath.toString();
            } catch (IOException ex) {
                throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), ex);
            }
        }
        @Override
        public Optional<Course> findCourseByTitle(String title) {
            return courseDao.findByTitle(title);
        }

        // In CourseController.java






    }